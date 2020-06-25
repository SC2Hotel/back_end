package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.RedisUtil;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.hotel.util.RedisUtil.roomKeyNamePrefix;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId) {
        return roomMapper.selectRoomsByHotelId(hotelId);
    }

    @Override
    public void insertRoomInfo(HotelRoom hotelRoom) {
        roomMapper.insertRoom(hotelRoom);
        redisUtil.delete(roomKeyNamePrefix+hotelRoom.getHotelId());
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomMapper.updateRoomInfo(hotelId,roomType,rooms);
        redisUtil.delete(roomKeyNamePrefix + hotelId);
    }

    @Override
    public Integer getRoomCurNum(Integer hotelId, String roomType) {
        return roomMapper.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public ResponseVO delRoomInfo(Integer roomId) {
        try{
            roomMapper.delRoomByRoomId(roomId);
            //TODO 这里需要保证缓存一致性
            return ResponseVO.buildSuccess("删除成功");
        }catch (Exception e){
            System.out.println(e);
            return ResponseVO.buildFailure("删除失败");
        }
    }


}
