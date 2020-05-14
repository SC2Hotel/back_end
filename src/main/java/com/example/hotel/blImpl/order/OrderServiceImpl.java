package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
            orderVO.setOrderState("已预订");
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
        if(order.getOrderState().equals("已失效"))
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
            return ResponseVO.buildSuccess(true);
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
        //TODO
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO getAllUsersOrdersInAHotel(int userId, int hotelId){
        //TODO
        return ResponseVO.buildSuccess();
    }

}
