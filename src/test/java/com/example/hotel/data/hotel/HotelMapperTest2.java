package com.example.hotel.data.hotel;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.*;

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
        ExecutorService executorService = Executors.newFixedThreadPool(600);
        ArrayList<Insertor> insertors = new ArrayList<>();
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            insertors.add(new Insertor());
            Future<Integer> integerFuture = executorService.submit(insertors.get(0));
            futures.add(integerFuture);
        }
        for (int i = 0; i < 300; i++) {
            try {
                Integer insRes = futures.get(i).get();
                System.out.println(insRes);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

//        int insertNum = 10000;
        Faker faker = new Faker(new Locale("zh-CN"));
//        for (int i = 0; i < insertNum; i++) {
//            Hotel hotel = new Hotel();
//            Address address = faker.address();
//            hotel.setHotelName(address.streetName()+"酒店");
//            hotel.setDescription("欢迎您入住");
//            hotel.setAddress(address.fullAddress());
//            hotel.setBizRegion(nextBizRegion());
//            hotel.setHotelStar(nextHotelStar());
//            hotel.setPhoneNum(faker.phoneNumber().cellPhone());
//            hotel.setRate(nextDouble(1,5));
//            hotel.setManagerId(6);
//            int row = hotelMapper.insertHotel(hotel);
//        }
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
    class Insertor implements Callable<Integer> {

        @Override
        public Integer call() {
            int insertNum = 300;
            Faker faker = new Faker(new Locale("zh-CN"));
            for (int i = 0; i < insertNum; i++) {
                Hotel hotel = new Hotel();
                Address address = faker.address();
                hotel.setHotelName(address.streetName()+"酒店");
                hotel.setDescription("欢迎您入住");
                hotel.setAddress(address.fullAddress());
                hotel.setBizRegion(HotelMapperTest2.nextBizRegion());
                hotel.setHotelStar(HotelMapperTest2.nextHotelStar());
                hotel.setPhoneNum(faker.phoneNumber().cellPhone());
                hotel.setRate(HotelMapperTest2.nextDouble(1,5));
                hotel.setManagerId(6);
                int row = hotelMapper.insertHotel(hotel);
            }
//                System.out.println("finish");
            return 1;
        }
    }
}

