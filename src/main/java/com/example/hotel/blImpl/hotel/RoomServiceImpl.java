package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.RedisUtil;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.hotel.util.RedisUtil.roomKeyNamePrefix;

@Service
@Slf4j
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
    public ResponseVO insertRoomInfo(HotelRoom hotelRoom) {
        List<HotelRoom> hotelRooms = roomMapper.selectRoomsByHotelId(hotelRoom.getHotelId());
        Set<RoomType> roomTypes = hotelRooms.stream().map(hotelRoom1 -> hotelRoom1.getRoomType()).collect(Collectors.toSet());
        if(roomTypes.contains(hotelRoom.getRoomType())){
            return ResponseVO.buildFailure("已存在该类型的客房");
        }
        roomMapper.insertRoom(hotelRoom);
        redisUtil.delete(roomKeyNamePrefix+hotelRoom.getHotelId());
        return ResponseVO.buildSuccess("添加成功");
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
            redisUtil.delete(roomKeyNamePrefix+roomId);
            return ResponseVO.buildSuccess("删除成功");
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseVO.buildFailure("删除失败");
        }
    }


}
