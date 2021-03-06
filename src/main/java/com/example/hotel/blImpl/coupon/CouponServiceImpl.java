package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.util.DateTimeUtil;
import com.example.hotel.util.RedisUtil;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.HotelTargetMoneyCouponVO;
import com.example.hotel.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.hotel.util.RedisUtil.COUPON_KEY_NAME_PREFIX;


@Service
@Slf4j
public class CouponServiceImpl implements CouponService {

    private final  TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;
    private final  TimeCouponStrategyImpl timeCouponStrategy;
    private final  VipCouponStrategyImpl vipCouponStrategy;
    private final CouponMapper couponMapper;
    @Autowired
    RedisUtil redisUtil;
    private static List<CouponMatchStrategy> strategyList = new ArrayList<>();

    @Autowired
    public CouponServiceImpl(TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy,
                             TimeCouponStrategyImpl timeCouponStrategy,
                             VipCouponStrategyImpl vipCouponStrategy,
                             CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
        this.targetMoneyCouponStrategy = targetMoneyCouponStrategy;
        this.timeCouponStrategy = timeCouponStrategy;
        this.vipCouponStrategy = vipCouponStrategy;
        strategyList.add(targetMoneyCouponStrategy);
        strategyList.add(timeCouponStrategy);
        strategyList.add(vipCouponStrategy);
    }



    @Override
    public List<Coupon> getMatchOrderCoupon(OrderVO orderVO) {

        List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());

        List<Coupon> availAbleCoupons = new ArrayList<>();

        for (int i = 0; i < hotelCoupons.size(); i++) {
            for (CouponMatchStrategy strategy : strategyList) {
                if (strategy.isMatch(orderVO, hotelCoupons.get(i))) {
                    availAbleCoupons.add(hotelCoupons.get(i));
                }
            }
        }

        return availAbleCoupons;
    }

    @Override
    public List<Coupon> getHotelAllCoupon(Integer hotelId) {
        if(redisUtil.hasKey(COUPON_KEY_NAME_PREFIX + hotelId)){
            return (List<Coupon>) redisUtil.get(COUPON_KEY_NAME_PREFIX + hotelId);
        }else {
            List<Coupon> hotelCoupons = couponMapper.selectByHotelId(hotelId);
            if(hotelCoupons==null || hotelCoupons.isEmpty() ){
                return hotelCoupons;
            }
            redisUtil.set(COUPON_KEY_NAME_PREFIX + hotelId,hotelCoupons);
            // 设置2小时过期
            redisUtil.expire(COUPON_KEY_NAME_PREFIX + hotelId, DateTimeUtil.TWO_HOURS_IN_SECOND);
            return hotelCoupons;
        }
    }

    @Override
    public CouponVO addHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setTargetMoney(couponVO.getTargetMoney());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setStatus(1);
        int result = couponMapper.insertCoupon(coupon);
        redisUtil.clean(COUPON_KEY_NAME_PREFIX + coupon.getHotelId());
        couponVO.setId(result);
        return couponVO;
    }
}
