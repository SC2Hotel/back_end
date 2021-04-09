package com.example.hotel.data.hotel;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Random;

/**
 * @author qin
 * @description 产生假数据
 * @date 2021-04-03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelMapperTest2 {
    @Autowired
    HotelMapper hotelMapper;

    //   `id` ,`hotelName` ,`hotelDescription` ,`address`, `bizRegion` ,`hotelStar` ,
    //  `phoneNum` , `rate` , `manager_id`
    // INSERT INTO `Hotel` VALUES (1,'汉庭酒店','欢迎您入住',NULL,'XiDan','Four',1829373819,4.8,1),
    // (2,'儒家酒店','欢迎您入住','南京市鼓楼区珠江路268号','XiDan','Four',1829373819,4.8,2),
    // (3,'桂圆酒店','欢迎您入住','南京市栖霞区珠江路268号','XiDan','Four',1829553719,4.8,6)
    static BizRegion[] bizRegions = new BizRegion[]{BizRegion.XiDan,
            BizRegion.FuZiMiao, BizRegion.BeiJingLu, BizRegion.WangFuJing,
            BizRegion.ZhuJiangXinCheng, BizRegion.XinJieKou
    };
    static HotelStar[] hotelStars = new HotelStar[]{HotelStar.Five,
            HotelStar.Four, HotelStar.Three
    };

    public static BizRegion nextBizRegion() {
        return bizRegions[new Random().nextInt(6)];
    }

    public static HotelStar nextHotelStar() {
        return hotelStars[new Random().nextInt(3)];
    }

    public static double nextDouble(final double min, final double max) {
        return min + ((max - min) * new Random().nextDouble());
    }

    @Test
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public void insertHotel() {
//        SqlSession sqlSession = getSqlSession();
//        zh-CN
        int insertNum = 0;
        Faker faker = new Faker(new Locale("zh-CN"));
        for (int i = 0; i < insertNum; i++) {
            Hotel hotel = new Hotel();
            hotel.setHotelName(faker.address().streetName()+"酒店");
            hotel.setDescription("欢迎您入住");
            hotel.setAddress(faker.address().fullAddress());
            hotel.setBizRegion(nextBizRegion());
            hotel.setHotelStar(nextHotelStar());
            hotel.setPhoneNum(faker.phoneNumber().cellPhone());
            hotel.setRate(nextDouble(1,5));
            hotel.setManagerId(6);
            int row = hotelMapper.insertHotel(hotel);
        }
        Hotel hotel = new Hotel();
        hotel.setHotelName(faker.address().streetName()+"酒店");
        hotel.setDescription("欢迎您入住");
        hotel.setAddress(faker.address().fullAddress());
        hotel.setBizRegion(nextBizRegion());
        hotel.setHotelStar(nextHotelStar());
        hotel.setPhoneNum(faker.phoneNumber().cellPhone());
        hotel.setRate(nextDouble(1,5));
        hotel.setManagerId(6);
        int row = hotelMapper.insertHotel(hotel);
//        Assert.assertEquals(1, row);
    }

}
