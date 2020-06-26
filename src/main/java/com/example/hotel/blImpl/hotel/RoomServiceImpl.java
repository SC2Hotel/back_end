package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.RedisUtil;
import com.example.hotel.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.hotel.util.RedisUtil.ROOM_KEY_NAME_PREFIX;

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
        redisUtil.delete(ROOM_KEY_NAME_PREFIX +hotelRoom.getHotelId());
        return ResponseVO.buildSuccess("添加成功");
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomMapper.updateRoomInfo(hotelId,roomType,rooms);
        redisUtil.delete(ROOM_KEY_NAME_PREFIX + hotelId);
    }

    @Override
    public Integer getRoomCurNum(Integer hotelId, String roomType) {
        return roomMapper.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public ResponseVO delRoomInfo(Integer roomId) {
        try{
            int hotelId = roomMapper.selectRoomsByRoomId(roomId).getHotelId();
            roomMapper.delRoomByRoomId(roomId);
            redisUtil.delete(ROOM_KEY_NAME_PREFIX +hotelId);
            return ResponseVO.buildSuccess("删除成功");
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseVO.buildFailure("删除失败");
        }
    }

    @Override
    public ResponseVO updateRoomNum(Integer roomId, Integer newRoomNum){
        try{
            HotelRoom hotelRoom = roomMapper.selectRoomsByRoomId(roomId);
            redisUtil.delete(ROOM_KEY_NAME_PREFIX + hotelRoom.getHotelId());
            roomMapper.updateRoomNum(newRoomNum, roomId);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseVO.buildFailure("更新失败");
        }
    }


}
