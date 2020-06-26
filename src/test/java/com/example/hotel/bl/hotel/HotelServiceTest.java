package com.example.hotel.bl.hotel;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.RoomType;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelAndRoomVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UpdateHotelVO;
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
@SpringBootTest
@RunWith(SpringRunner.class)
public class HotelServiceTest {

    @Autowired
    HotelService hotelService;

    @Test(expected = ServiceException.class)
    public void addHotel1() throws ServiceException {
        HotelVO hotelVO = new HotelVO();
        hotelVO.setManagerId(0);
        hotelService.addHotel(hotelVO);
        Assert.fail();
    }

    @Test
    public void addHotel2() {
        HotelVO hotelVO = new HotelVO();
        hotelVO.setManagerId(6);
        hotelVO.setName("测试酒店");
        hotelVO.setBizRegion("西单");
        hotelVO.setHotelStar("Four");
        int row = 0;
        try {
            hotelService.addHotel(hotelVO);
        } catch (ServiceException e) {
            Assert.fail();
        }
        Assert.assertEquals(0, row);
    }

    @Test
    public void updateRoomInfo() {
        Integer numBefore = hotelService.getRoomCurNum(1, "DoubleBed");
        hotelService.updateRoomInfo(1, "DoubleBed", 1);
        Integer numAfter = hotelService.getRoomCurNum(1, "DoubleBed");
//        System.out.println(numBefore);
//        System.out.println(numAfter);
        Assert.assertEquals(1, numBefore - numAfter);
    }

    @Test
    public void retrieveHotels() {
        List<HotelVO> hotels = hotelService.retrieveHotels();
        assertTrue(hotels.size() > 1);
    }

    @Test
    public void retrieveHotelDetails() {
        HotelVO hotelDetails = hotelService.retrieveHotelDetails(1);
        assertNotNull(hotelDetails);
    }

    @Test
    public void getRoomCurNum() {
        Integer roomCurNum = hotelService.getRoomCurNum(1, "Family");
        assertTrue(roomCurNum > 5);
    }

    @Test
    public void retrieveHotelsByBizAndAdd() {
        List<HotelVO> hotels = hotelService.retrieveHotelsByBizAndAdd("西单", "鼓楼");
        assertTrue(hotels.size()>0);
    }

    @Test
    public void retrieveAllBizRegions() {
//        List<BizRegion> bizRegions = hotelService.retrieveAllBizRegions();
//        assertTrue(bizRegions.size()>0);
    }

    @Test
    public void retrieveHotelsByHotelAndRoomVO() {
        HotelAndRoomVO hotelAndRoomVO = new HotelAndRoomVO();
        hotelAndRoomVO.setRoomType("Family");
        hotelAndRoomVO.setHotelStar("Four");
        hotelAndRoomVO.setName("酒店");
        hotelAndRoomVO.setRate(4.0);
        hotelAndRoomVO.setBizRegion("西单");
        hotelAndRoomVO.setAddress("区");
        hotelAndRoomVO.setLoPrice(80.0);
        hotelAndRoomVO.setHiPrice(580.0);
        List<HotelVO> hotels = hotelService.retrieveHotelsByHotelAndRoomVO(hotelAndRoomVO);
        assertTrue(hotels.size()>0);

    }

    @Test
    public void updateHotelInfo() {
        UpdateHotelVO updateHotelVO = new UpdateHotelVO();
        updateHotelVO.setId(1);
        updateHotelVO.setDescription("welcome");
        updateHotelVO.setBizRegion(BizRegion.FuZiMiao.toString());
        ResponseVO responseVO = hotelService.updateHotelInfo(updateHotelVO);
        assertTrue(responseVO.getSuccess());
    }
}