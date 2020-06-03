package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface OrderMapper {

    int addOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getUserOrders(@Param("userid") int userid);

    /**
     * 获得订单的价格
     * @param orderId
     * @return
     */
    int getPrice(int orderId);
    /**
     * return value is the line which match the orderId
     * also means that if value == 1 equals success else fault
     * @param orderid
     * @return line which match the orderId
     */
    int annulOrder(@Param("orderid") int orderid);

    Order getOrderById(@Param("orderid") int orderid);

    /**
     * 将订单状态设置为异常
     * @param orderid
     * @return
     */
    int exceptionOrder(int orderid);

    /**
     * 将订单状态设置为执行
     * @param orderId checkInDate
     * @return
     */
    int executeOrder(int orderId, String checkInDate);

    int checkOutOrder(int orderId, String checkOutDate);

    /**
     * 设置订单状态
     * @param orderId
     * @param state
     * @return
     */
    int setOrderState(int orderId, String state);
}
