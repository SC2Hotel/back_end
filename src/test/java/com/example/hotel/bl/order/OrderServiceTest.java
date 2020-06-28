package com.example.hotel.bl.order;

import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.enums.OrderState;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Order;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author qin
 * @date 2020-06-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    RoomMapper roomMapper;
    @Test
    public void addOrder() {
        OrderVO orderVO = new OrderVO();
        orderVO.setUserId(4);
        orderVO.setHotelId(3);
        orderVO.setCheckInDate("2020-06-03");
        orderVO.setCheckOutDate("2020-06-04");
        orderVO.setRoomType("Family");
        orderVO.setRoomNum(1);
        orderVO.setPeopleNum(1);
        orderVO.setHaveChild(false);
        orderVO.setCreateDate("2020-07-01");
        orderVO.setPrice(399.0);
        orderVO.setClientName("qin");
        orderVO.setOrderState("已预订");
        ResponseVO responseVO = orderService.addOrder(orderVO);
        assertTrue(responseVO.getSuccess());

    }

    @Test
    public void getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        assertTrue(orders.size()>0);
    }

    @Test
    public void getUserOrders() {
        List<Order> orders = orderService.getUserOrders(4);
        assertTrue(orders.size()>0);
    }

    @Test
    public void annulOrder() {
        ResponseVO responseVO = orderService.annulOrder(2);
        assertTrue(responseVO.getSuccess());
    }

    @Test
    public void getHotelOrders() {
        List<Order> orders = orderService.getHotelOrders(3);
        assertTrue(orders.size()>0);
    }

    @Test
    public void getBookedHotels() {
        ResponseVO responseVO = orderService.getBookedHotels(4);
        assertTrue(responseVO.getSuccess());
    }

    @Test
    public void getAllUsersOrdersInAHotel() {
        ResponseVO responseVO = orderService.getAllUsersOrdersInAHotel(4, 3);
        assertTrue(responseVO.getSuccess());
    }

    @Test
    public void executeOrder() {
        ResponseVO responseVO = orderService.executeOrder(1, 4);
        assertTrue(responseVO.getSuccess());

    }

    @Test
    public void getHotelExceptionOrder() {
        List<Order> orders = orderService.getHotelExceptionOrder(3);
        assertTrue(orders.size()>0);

    }

    @Test
    public void executeExceptionOrder() {
        ResponseVO responseVO = orderService.executeExceptionOrder(7);
        assertTrue(responseVO.getSuccess());
        Assert.assertEquals("超过最长延迟入住时间", responseVO.getContent());
    }

    @Test
    public void checkOutOrder() {
        orderService.checkOutOrder(4);
        assertTrue(true);
    }

    @Test
    public void comment(){
        CommentVO commentVO = new CommentVO();
        commentVO.setOrderId(5);
        commentVO.setContent("测试");
        commentVO.setScore(4.0);
        orderService.comment(commentVO);
        Assert.assertTrue(orderMapper.getOrderById(commentVO.getOrderId()).getOrderState().equals(OrderState.evaluation.toString()));
    }
}