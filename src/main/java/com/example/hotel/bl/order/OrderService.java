package com.example.hotel.bl.order;


import com.example.hotel.po.Order;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     * @return
     */
    List<Order> getAllOrders();

    /**
     * 获得指定用户的所有订单信息
     * @param userid
     * @return
     */
    List<Order> getUserOrders(int userid);

    /**
     * 用户撤销订单
     * @param orderid
     * @return
     */
    ResponseVO annulOrder(int orderid);

    /**
     * 查看酒店的所有订单
     * @param hotelId
     * @return
     */
    List<Order> getHotelOrders(Integer hotelId);

    /**
     * 获取用户预定过的所有酒店
     * @param userId
     * @return
     */
    ResponseVO getBookedHotels(int userId);

    /**
     * 获取用户在某个酒店的所有订单
     * @param userId
     * @param hotelId
     * @return
     */
    ResponseVO getAllUsersOrdersInAHotel(int userId, int hotelId);

    /**
      * @description: 执行订单，并且增加用户的信用值
      * @param: orderId userId
      * @return: ResponseVO
      * @author: pkun
      * @date: 2020/6/1
      */
    ResponseVO executeOrder(int orderId, int userId);

    /**
      * @description: 酒店管理人员可以获得该酒店所有异常订单
      * @param: hotelId
      * @return: List Order
      * @author: pkun
      * @date: 2020/6/3
      */
    List<Order> getHotelExceptionOrder(int hotelId);

    /**
      * @description: 延时入住功能，酒店管理人员可以撤销异常订单改为已执行，并且回复信用值
      * @param: orderId
      * @return: ResponseVO
      * @author: pkun
      * @date: 2020/6/3
      */
    ResponseVO executeExceptionOrder(int orderId);

    /**
      * @description: 退房，会更新订单的退房时间
      * @param: orderId
      * @return: void
      * @author: pkun
      * @date: 2020/6/3
      */
    void checkOutOrder(int orderId);

    ResponseVO comment(CommentVO commentVO);

    ResponseVO getOrderComment(int orderId);

    ResponseVO getHotelComment(int hotelId);
}
