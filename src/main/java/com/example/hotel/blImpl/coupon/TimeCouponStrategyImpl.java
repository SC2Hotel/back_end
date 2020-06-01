package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.util.DateTimeUtil;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种限时优惠策略
     *
     * @param orderVO
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {

        if (coupon.getCouponType() == 4) {
            LocalDateTime createTime = DateTimeUtil.dateTimeStr2LocalDateTime(orderVO.getCreateDate(),"12:00:00");
            return (createTime.compareTo(coupon.getStartTime()) > 0
                    && createTime.compareTo(coupon.getEndTime()) < 0);
        }

        return false;
    }
}
