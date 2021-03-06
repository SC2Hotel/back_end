package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.CommentService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.enums.CreditChangeReason;
import com.example.hotel.enums.OrderState;
import com.example.hotel.po.Comment;
import com.example.hotel.po.CreditChange;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.DateTimeUtil;
import com.example.hotel.util.RedisUtil;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.hotel.enums.OrderState.*;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private static final String RESERVE_ERROR = "预订失败";
    private static final String CREDIT_LACK = "信用值不足";
    private static final String ROOMNUM_LACK = "预订房间数量剩余不足";
    private static final Integer LATEST_ANNUL_INTERVAL = 6;

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;
    @Autowired
    CommentService commentService;
    @Autowired
    RedisUtil redisUtil;

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
            if(user.getCredit()<0){
                return ResponseVO.buildFailure(CREDIT_LACK);
            }
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
        } catch (Exception e) {
            log.error(e.getMessage());
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
            // CheckIn日期
            String checkInDate = order.getCheckInDate();
            // 最晚订单执行时间设定为 CheckIn日期的 LATEST_CHECK_IN_TIME
            LocalDateTime latestCheckInDateTime = DateTimeUtil.dateTimeStr2LocalDateTime(checkInDate,DateTimeUtil.LATEST_CHECK_IN_TIME);
            LocalDateTime curDateTime = LocalDateTime.now();
            // 如果距离最晚订单执行时间不足 6 个小时,当前时间+6h > 最晚订单执行时间
            if(curDateTime.plusHours(LATEST_ANNUL_INTERVAL).compareTo(latestCheckInDateTime) > 0 ){
                // 撤销的同时扣除信用值，信用值为订单的（总价值*1/2）
                Double creditToMinus = order.getPrice()/2;
                CreditChange creditChange = new CreditChange();
                creditChange.setChangeNum(-creditToMinus);
                creditChange.setOrderId(orderId);
                creditChange.setUserId(order.getUserId());
                creditChange.setReason(CreditChangeReason.annulOrder.toString());
                User userInfo = accountService.getUserInfo(order.getUserId());
                creditChange.setCredit(userInfo.getCredit());
                int row = accountService.updateUserCredit(creditChange);
                return ResponseVO.buildSuccess(row);
            }
            return ResponseVO.buildSuccess(1);
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
        List<Order> orders = this.getUserOrders(userId);
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

        orderMapper.executeOrder(orderId, LocalDateTime.now().toString().substring(0, 10));
        double credit = order.getPrice();
        CreditChange creditChange = new CreditChange();
        creditChange.setChangeNum(credit);
        creditChange.setOrderId(order.getId());
        creditChange.setUserId(order.getUserId());
        creditChange.setReason(CreditChangeReason.executeOrder.toString());
        User userInfo = accountService.getUserInfo(order.getUserId());
        creditChange.setCredit(userInfo.getCredit());
        if(accountService.updateUserCredit(creditChange)==0){
            return ResponseVO.buildFailure("修改用户信用值失败");
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getHotelExceptionOrder(int hotelId){
        return this.getHotelOrders(hotelId).stream().filter(order -> order.getOrderState().equals(exception.toString())).collect(Collectors.toList());
    }

    @Override
    public ResponseVO executeExceptionOrder(int orderId){
        Order order;
        try{
            order = orderMapper.getOrderById(orderId);
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }

        if(DateTimeUtil.compare(order.getCheckOutDate(), "12:00:00", LocalDateTime.now(), 0, 0) > 0){
            try{
                orderMapper.executeOrder(orderId, LocalDateTime.now().toString().substring(0, 10));
                CreditChange creditChange = new CreditChange();
                creditChange.setChangeNum(order.getPrice());
                creditChange.setOrderId(order.getId());
                creditChange.setUserId(order.getUserId());
                creditChange.setReason(CreditChangeReason.executeOrder.toString());
                User userInfo = accountService.getUserInfo(order.getUserId());
                creditChange.setCredit(userInfo.getCredit());
                accountService.updateUserCredit(creditChange);
            }catch (Exception e){
                return ResponseVO.buildFailure(e.getMessage());
            }
            return ResponseVO.buildSuccess();
        }
        else{
            try{
                orderMapper.dealOutCheckoutDate(orderId);
                hotelService.updateRoomInfo(order.getHotelId(), order.getRoomType(), -order.getRoomNum());
            }catch (Exception e){
                return ResponseVO.buildFailure(e.getMessage());
            }
            return ResponseVO.buildSuccess("超过最长延迟入住时间");
        }

    }

    @Override
    public void checkOutOrder(int orderId){
        Order order;
        order = orderMapper.getOrderById(orderId);
        hotelService.updateRoomInfo(order.getHotelId(), order.getRoomType(), -order.getRoomNum());
        orderMapper.checkOutOrder(orderId, LocalDateTime.now().toString().substring(0, 10));
    }

    @Override
    public ResponseVO comment(CommentVO commentVO) {
        Order order = orderMapper.getOrderById(commentVO.getOrderId());
        if(OrderState.execute.toString().equals(order.getOrderState())){
            ResponseVO responseVO = commentService.addComment(commentVO);
            if(responseVO.getSuccess()) orderMapper.evaluationOrder(order.getId());
            return responseVO;
        }else{
            return ResponseVO.buildFailure("订单状态不是已执行");
        }

    }

    @Override
    public ResponseVO getOrderComment(int orderId) {
        try{
            Comment comment = commentService.getComment(orderId);
            return ResponseVO.buildSuccess(comment);
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }

    }

    @Override
    public ResponseVO getHotelComment(int hotelId) {
        try{
            List<Comment> comments = commentService.getAllHotelComment(hotelId);
            return ResponseVO.buildSuccess(comments);
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }

    }
}
