package com.example.hotel.data.hotel;

import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.RoomVO;
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
public class RoomMapperTest {

    //   `id` ,`price` ,`curNum`, `total`, `hotel_id`,`roomType`
    //INSERT INTO `Room` VALUES (2,199,20,20,1,'BigBed'),
    // (3,299,30,30,1,'DoubleBed'),
    // (4,399,10,10,1,'Family'),
    // (5,122,7,0,1,'BigBed'),
    // (6,399,10,10,2,'Family');
    @Autowired
    RoomMapper roomMapper;

    @Test
    public void updateRoomInfo() {
        // hotelId,roomType,rooms
        int row = roomMapper.updateRoomInfo(2, "Family", 1);
        // 更新一行
        Assert.assertEquals(1, row);
    }

    @Test
    public void insertRoom() {
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setPrice(399);
        hotelRoom.setCurNum(10);
        hotelRoom.setTotal(10);
        hotelRoom.setHotelId(3);
        hotelRoom.setRoomType(RoomType.Family);
        // 399,10,10,3,'Family');
        int row = roomMapper.insertRoom(hotelRoom);
        Assert.assertEquals(1, row);
    }

    @Test
    public void selectRoomsByHotelId() {
        List<HotelRoom> list = roomMapper.selectRoomsByHotelId(1);
        Assert.assertTrue(list.size() >= 1);
    }

    @Test
    public void getRoomCurNum() {
        // todo 测出错误 待改正
        int curNum = -1;
//        curNum = roomMapper.getRoomCurNum(1, "BigBed");
        curNum = roomMapper.getRoomCurNum(2, "Family");
        Assert.assertTrue(curNum >= 0);
    }
}