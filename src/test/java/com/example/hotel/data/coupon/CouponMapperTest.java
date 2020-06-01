package com.example.hotel.data.coupon;

import com.example.hotel.po.Coupon;
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
 * @date 2020-05-23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponMapperTest {
    @Autowired
    CouponMapper couponMapper;

    //   `id`,`description`,`hotelId`,`couponType`,`couponName`, `target_money`,
    //  `discount` , `status` ,`start_time` , `end_time` , `discount_money`
    // INSERT INTO `Coupon` VALUES (2,'满500-100优惠',2,3,'满减优惠券',500,0,1,NULL,NULL,100);
    @Test
    public void insertCoupon() {
        Coupon coupon = new Coupon();
        coupon.setCouponName("满600减100优惠");
        coupon.setHotelId(2);
        coupon.setCouponType(3);
        coupon.setCouponName("满减优惠券");
        coupon.setTargetMoney(600);
        coupon.setDiscount(0);
        coupon.setStatus(0);
        coupon.setDiscountMoney(100);
        int row = couponMapper.insertCoupon(coupon);
        Assert.assertEquals(1,row);
    }

    @Test
    public void selectByHotelId() {
        List<Coupon> list = couponMapper.selectByHotelId(2);
        Assert.assertTrue(list.size()>=1);
    }
}
