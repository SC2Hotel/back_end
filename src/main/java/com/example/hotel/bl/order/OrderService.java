package com.example.hotel.bl.order;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.Order;
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
     * 撤销订单
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
}
