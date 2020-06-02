package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.enums.OrderState;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.DateTimeUtil;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.hotel.enums.OrderState.Booked;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType());
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState(OrderState.Booked.toString());
            User user = accountService.getUserInfo(orderVO.getUserId());
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getUserOrders(int userid) {
        return orderMapper.getUserOrders(userid);
    }

    @Override
    public ResponseVO annulOrder(int orderId) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        Order order = orderMapper.getOrderById(orderId);
        if(order==null)
        {
            return ResponseVO.buildFailure("ERROR ORDER ID");
        }
        if(!order.getOrderState().equals(OrderState.Booked.toString()))
        {
            return ResponseVO.buildFailure("Order status error");
        }
        int hotelId = order.getHotelId();
        int roomNum = order.getRoomNum();
        String roomType = order.getRoomType();
        hotelService.updateRoomInfo(hotelId, roomType, -roomNum);

        int res = orderMapper.annulOrder(orderId);
        if(res==1)
        {
            String checkInDate = order.getCheckInDate(); // CheckIn日期
            // 最晚订单执行时间设定为 CheckIn日期的 LATEST_CHECK_IN_TIME
            LocalDateTime latestCheckInDateTime = DateTimeUtil.dateTimeStr2LocalDateTime(checkInDate,DateTimeUtil.LATEST_CHECK_IN_TIME);
            LocalDateTime curDateTime = LocalDateTime.now();
            if(curDateTime.plusHours(6).compareTo(latestCheckInDateTime) > 0 ){ // 如果距离最晚订单执行时间不足 6 个小时,当前时间+6h > 最晚订单执行时间
                // 撤销的同时扣除信用值，信用值为订单的（总价值*1/2）
                Double creditToMinus = order.getPrice()/2;
                int row = accountService.updateUserCredit(order.getUserId(),creditToMinus);
                return ResponseVO.buildSuccess(row);
            }
            return ResponseVO.buildSuccess(0);
        }
        else
        {
            return ResponseVO.buildFailure("UPDATE FAILURE");
        }

    }

    /**
     * @param hotelId
     * @return
     */
    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = this.getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }

    @Override
    public ResponseVO getBookedHotels(int userId){
        List<Order> orders = this.getAllOrders();
        List<HotelVO> hotels = new LinkedList<>();
        Set<Integer> hotelIds = new HashSet<>();
        try{
            for(Order order : orders){
                if(!hotelIds.contains(order.getHotelId())){
                    hotelIds.add(order.getHotelId());
                    hotels.add(hotelService.retrieveHotelDetails(order.getHotelId()));
                }
            }
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
        return ResponseVO.buildSuccess(hotels);
    }

    @Override
    public ResponseVO getAllUsersOrdersInAHotel(int userId, int hotelId){
        List<Order> orders = this.getUserOrders(userId);
        return ResponseVO.buildSuccess(orders.stream().filter(order -> order.getHotelId()==hotelId).collect(Collectors.toList()));
    }


    @Override
    public ResponseVO executeOrder(int orderId, int userId){
        Order order;
        try {
            order = orderMapper.getOrderById(orderId);
        }catch (Exception e){
            return ResponseVO.buildFailure("订单不存在");
        }

        if (!order.getOrderState().equals(Booked.toString())){
            return ResponseVO.buildFailure("无效订单");
        }

        double credit = order.getPrice() * 0.02;

        if(accountService.updateUserCredit(userId, -credit)==0){
            return ResponseVO.buildFailure("修改用户信用值失败");
        }

        return ResponseVO.buildSuccess(true);
    }

}
