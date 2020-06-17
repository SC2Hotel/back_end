package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.HotelTargetMoneyCouponVO;
import com.example.hotel.vo.OrderVO;
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
public class CouponServiceTest {

    @Autowired
    CouponService couponService;
    @Test
    public void getMatchOrderCoupon() {
        OrderVO orderVO = new OrderVO();
        orderVO.setPrice(10000.0);
        orderVO.setCheckOutDate("2020-06-01");
        orderVO.setCheckInDate("2021-06-01");
        orderVO.setHotelId(2);
        List<Coupon> coupons = couponService.getMatchOrderCoupon(orderVO);
        Assert.assertTrue(coupons.size()>0);
    }

    @Test
    public void getHotelAllCoupon() {
        List<Coupon> coupons = couponService.getHotelAllCoupon(2);
        Assert.assertTrue(coupons.size()>0);
    }

    @Test
    public void addHotelTargetMoneyCoupon() {
        HotelTargetMoneyCouponVO couponVO = new HotelTargetMoneyCouponVO();
        couponVO.setHotelId(2);
        couponVO.setType(3);
        couponVO.setDescription("满1000减200");
        couponVO.setName("满1000减200优惠卷");
        couponVO.setStatus(1);
        couponVO.setTargetMoney(1000);
        couponVO.setDiscountMoney(200);
        CouponVO res = couponService.addHotelTargetMoneyCoupon(couponVO);
        Assert.assertNotNull(res);
    }
}