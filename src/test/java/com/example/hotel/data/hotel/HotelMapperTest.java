package com.example.hotel.data.hotel;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelAndRoomVO;
import com.example.hotel.vo.HotelVO;
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
public class HotelMapperTest {

    @Autowired
    HotelMapper hotelMapper;

    //   `id` ,`hotelName` ,`hotelDescription` ,`address`, `bizRegion` ,`hotelStar` ,
    //  `phoneNum` , `rate` , `manager_id`
    // INSERT INTO `Hotel` VALUES (1,'汉庭酒店','欢迎您入住',NULL,'XiDan','Four',1829373819,4.8,1),
    // (2,'儒家酒店','欢迎您入住','南京市鼓楼区珠江路268号','XiDan','Four',1829373819,4.8,2),
    // (3,'桂圆酒店','欢迎您入住','南京市栖霞区珠江路268号','XiDan','Four',1829553719,4.8,6)
    @Test
    public void insertHotel() {
        Hotel hotel = new Hotel();
        hotel.setHotelName("南京国际会议中心");
        hotel.setDescription("欢迎您入住");
        hotel.setAddress("南京市栖霞区仙林大道163号");
        hotel.setBizRegion(BizRegion.valueOf("XiDan"));
        hotel.setHotelStar(HotelStar.valueOf("Four"));
        hotel.setPhoneNum("1829373819");
        hotel.setRate(4.8);
        hotel.setManagerId(6);
        int row = hotelMapper.insertHotel(hotel);
        Assert.assertEquals(1, row);
    }

    @Test
    public void selectAllHotel() {
        List<HotelVO> list = hotelMapper.selectAllHotel();
        Assert.assertTrue(list.size() >= 1);
    }

    @Test
    public void selectById() {
        HotelVO hotelVO = hotelMapper.selectById(1);
        Assert.assertEquals("汉庭酒店", hotelVO.getName());
    }

    @Test
    public void retrieveHotelsByHotelAndRoomVO() {
        HotelAndRoomVO hotelAndRoomVO = new HotelAndRoomVO();
        hotelAndRoomVO.setBizRegion("XiDan");
        hotelAndRoomVO.setAddress("北京");
        hotelAndRoomVO.setRoomType("BigBed");
        hotelAndRoomVO.setLoPrice(100.0);
        hotelAndRoomVO.setHiPrice(300.0);
        hotelAndRoomVO.setName("汉");
        hotelAndRoomVO.setHotelStar("Four");
        hotelAndRoomVO.setRate(4.7);
        List<HotelVO> hotelVOS = hotelMapper.retrieveHotelsByHotelAndRoomVO(hotelAndRoomVO);
        Assert.assertTrue(hotelVOS.size() >= 1);
    }
}