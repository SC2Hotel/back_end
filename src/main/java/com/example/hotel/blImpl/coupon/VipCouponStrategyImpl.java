package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/6/26 11:35
 */
@Service
@Slf4j
public class VipCouponStrategyImpl implements CouponMatchStrategy {

    @Autowired
    AccountMapper accountMapper;

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon){
        try{
            Vip vip = accountMapper.getVipById(orderVO.getUserId());
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        User user = accountMapper.getAccountById(orderVO.getUserId());
        if(coupon.getCouponType()== 5 &&user.getCredit()>=coupon.getTargetMoney()){
            return true;
        }
        return false;
    }

}
