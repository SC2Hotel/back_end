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

import static com.example.hotel.util.DateTimeUtil.TWO_HOURS_IN_SECOND;


@Service
@Slf4j
public class CouponServiceImpl implements CouponService {


    private final  TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;

    private final  TimeCouponStrategyImpl timeCouponStrategy;
    private final CouponMapper couponMapper;
    @Autowired
    RedisUtil redisUtil;
    private static List<CouponMatchStrategy> strategyList = new ArrayList<>();

    private static final String keyNamePrefix = "hotel:coupon:";
    @Autowired
    public CouponServiceImpl(TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy,
                             TimeCouponStrategyImpl timeCouponStrategy,
                             CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
        this.targetMoneyCouponStrategy = targetMoneyCouponStrategy;
        this.timeCouponStrategy = timeCouponStrategy;
        strategyList.add(targetMoneyCouponStrategy);
        strategyList.add(timeCouponStrategy);
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
        if(redisUtil.hasKey(keyNamePrefix + hotelId)){
//            log.info("get from redis key="+keyNamePrefix+hotelId);
            return (List<Coupon>) redisUtil.get(keyNamePrefix + hotelId);
        }else {
//            log.info("get from mysql "+ keyNamePrefix +hotelId);
            List<Coupon> hotelCoupons = couponMapper.selectByHotelId(hotelId);
            if(hotelCoupons==null || hotelCoupons.size()==0){
                return hotelCoupons;
            }
            redisUtil.set(keyNamePrefix + hotelId,hotelCoupons);
            redisUtil.expire(keyNamePrefix + hotelId, DateTimeUtil.TWO_HOURS_IN_SECOND); // 设置2小时过期
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
        redisUtil.clean(keyNamePrefix + coupon.getHotelId());
        couponVO.setId(result);
        return couponVO;
    }
}
