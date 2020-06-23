package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
@Api(tags = "OrderController订单相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("增加订单")
    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @ApiOperation("获取所有的订单")
    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @ApiOperation("根据userid获取订单")
    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }

    @ApiOperation("撤销订单，只能撤销预定中的")
    @PostMapping("/{orderid}/annulOrder")
    public ResponseVO annulOrder(@PathVariable Integer orderid){
        return orderService.annulOrder(orderid);
    }

    @ApiOperation("获取某个酒店的所有订单")
    @GetMapping("/{hotelId}/allOrders")
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }

    @ApiOperation("获取自己预定过得所有酒店")
    @GetMapping("/{userId}/getBookedHotels")
    public ResponseVO getBookedHotels(@PathVariable int userId){
        return orderService.getBookedHotels(userId);
    }

    @ApiOperation("获取用户在某个酒店的所有订单")
    @GetMapping("/{userId}/{hotelId}/getAllUsersOrdersInAHotel")
    public ResponseVO getAllUsersOrdersInAHotel(@PathVariable int userId, @PathVariable int hotelId){
        return orderService.getAllUsersOrdersInAHotel(userId, hotelId);
    }

    @ApiOperation("执行某个用户的订单，只能执行已预订类型的订单")
    @PostMapping("/{userId}/{orderId}/executeOrder")
    public ResponseVO executeOrder(@PathVariable int userId, @PathVariable int orderId){
        return orderService.executeOrder(orderId, userId);
    }

    @ApiOperation("获取所有异常订单")
    @GetMapping("/{hotelId}/getAllExceptionOrder")
    public ResponseVO getHotelAllExceptionOrder(@PathVariable int hotelId){
        try{
            return ResponseVO.buildSuccess(orderService.getHotelExceptionOrder(hotelId));
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @ApiOperation("延时入住，只针对异常订单")
    @PostMapping("/{orderId}/delayCheckIn")
    public ResponseVO delayCheckIn(@PathVariable int orderId){
        try{
            return orderService.executeExceptionOrder(orderId);
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @ApiOperation("退房")
    @PostMapping("/{orderId}/checkOut")
    public ResponseVO checkOut(@PathVariable int orderId){
        try{
            orderService.checkOutOrder(orderId);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @ApiOperation("对某个订单进行评论，只能评论已执行的订单")
    @PostMapping("/{orderId}/comment")
    public ResponseVO comment(@RequestBody CommentVO commentVO) {
        return orderService.comment(commentVO);
    }

    @ApiOperation("获取某个订单的评论")
    @GetMapping("/{orderId}/getOrderComment")
    public ResponseVO getOrderComment(@PathVariable("orderId") int orderId){
        return orderService.getOrderComment(orderId);
    }

    @ApiOperation("获取某个酒店的所有评论")
    @GetMapping("/{hotelId}/getHotelComment")
    public ResponseVO getHotelComment(@PathVariable("hotelId") int hotelId){
        return orderService.getHotelComment(hotelId);
    }
}
