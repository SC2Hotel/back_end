package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }

    @GetMapping("/{orderid}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderid){
        return orderService.annulOrder(orderid);
    }

    @GetMapping("/{hotelId}/allOrders")
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }

    @GetMapping("/{userId}/getBookedHotels")
    public ResponseVO getBookedHotels(@PathVariable int userId){
        return orderService.getBookedHotels(userId);
    }

    @GetMapping("/{userId}/{hotelId}/getAllUsersOrdersInAHotel")
    public ResponseVO getAllUsersOrdersInAHotel(@PathVariable int userId, @PathVariable int hotelId){
        return orderService.getAllUsersOrdersInAHotel(userId, hotelId);
    }

    @PostMapping("/{userId}/{orderId}/executeOrder")
    public ResponseVO executeOrder(@PathVariable int userId, @PathVariable int orderId){
        return orderService.executeOrder(orderId, userId);
    }

    @GetMapping("/{hotelId}/getAllExceptionOrder")
    public ResponseVO getHotelAllExceptionOrder(@PathVariable int hotelId){
        try{
            return ResponseVO.buildSuccess(orderService.getHotelExceptionOrder(hotelId));
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @PostMapping("/{orderId}/delayCheckIn")
    public ResponseVO delayCheckIn(@PathVariable int orderId){
        try{
            orderService.executeExceptionOrder(orderId);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @PostMapping("/{orderId}/checkOut")
    public ResponseVO checkOut(@PathVariable int orderId){
        try{
            orderService.checkOutOrder(orderId);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

}
