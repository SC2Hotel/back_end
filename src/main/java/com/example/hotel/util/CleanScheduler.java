package com.example.hotel.util;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.enums.OrderState;
import com.example.hotel.po.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author qin
 * @date 2020-05-31
 */
@Component
public class CleanScheduler {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    AccountService accountService;

    @Scheduled(cron = "0 1 22 * * ?")
    public void cleanExpiredOrder(){
        List<Order> orders = orderMapper.getAllOrders();
        for (Order order :orders) {
            System.out.println(order.getId());
            LocalDateTime orderLatestCheckInTime = DateTimeUtil.
                    dateTimeStr2LocalDateTime(order.getCheckInDate(),DateTimeUtil.LATEST_CHECK_IN_TIME);
            if(LocalDateTime.now().compareTo(orderLatestCheckInTime) > 0
                    && order.getOrderState().equals(OrderState.Booked.toString())){
                // 现在的时间超过了最晚入住时间 且 订单状态为已预定
                orderMapper.exceptionOrder(order.getId());
                // 用户的信用值会被减少
                Double creditToMinus = order.getPrice();
                accountService.updateUserCredit(order.getUserId(),creditToMinus);
            }
        }
    }
}
