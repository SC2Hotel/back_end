package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import com.example.hotel.util.DateTimeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.hotel.enums.OrderState.*;
import static org.junit.Assert.*;

/**
 * @author qin
 * @date 2020-05-23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTest {

    // OrderList(userId,hotelId,hotelName,checkInDate,checkOutDate,roomType,roomNum,
    // peopleNum,haveChild,createDate,price,clientName,phoneNumber,orderState)
    @Autowired
    OrderMapper orderMapper;

    @Test
    public void addOrder() {
        Order order = new Order();
        order.setUserId(4);
        order.setHotelName("桂圆酒店");
        order.setCheckInDate("2020-06-01");
        order.setCheckOutDate("2020-06-02");
        order.setHotelId(3);
        order.setPrice(399.0);
        order.setClientName("qin");
        order.setPhoneNumber("15521232123");
        order.setRoomType("Family");
        order.setRoomNum(1);
        order.setHaveChild(false);
        order.setPeopleNum(1);
        order.setOrderState("已预订");
        order.setCreateDate("2020-05-23");
        int row = orderMapper.addOrder(order);
        Assert.assertEquals(1,row);
    }

    @Test
    public void getAllOrders() {
        List<Order> allOrders = orderMapper.getAllOrders();
        Assert.assertTrue(allOrders.size() > 0);
    }

    @Test
    public void getUserOrders() {
        List<Order> userOrders = orderMapper.getUserOrders(4);
        Assert.assertTrue(userOrders.size() > 0);
    }

    @Test
    public void annulOrder() {
        int row = orderMapper.annulOrder(1);
        Assert.assertEquals(1,row);
    }

    @Test
    public void getOrderById() {
        Order order = orderMapper.getOrderById(1);
        Assert.assertNotNull(order);
    }
    
    @Test
    public void exceptionOrder(){
        orderMapper.exceptionOrder(1);
        Order order = orderMapper.getOrderById(1);
        Assert.assertEquals(order.getOrderState(), exception.toString());
    }

    @Test
    public void executeOrder(){
        orderMapper.executeOrder(1, LocalDateTime.now().toString().substring(0, 10));
        Order order = orderMapper.getOrderById(1);
        Assert.assertEquals(order.getOrderState(), execute.toString());
    }

    @Test
    public void checkOutOrder(){
        orderMapper.checkOutOrder(1, LocalDateTime.now().toString().substring(0, 10));
        Order order = orderMapper.getOrderById(1);
        Assert.assertEquals(order.getCheckOutDate(), LocalDateTime.now().toString().substring(0, 10));
    }


}
