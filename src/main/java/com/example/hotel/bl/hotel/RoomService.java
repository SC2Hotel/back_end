package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface RoomService {

    /**
     * 获取某个酒店的全部房间信息
     * @param hotelId
     * @return
     */
    List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId);

    /**
     * 添加酒店客房信息
     * @param hotelRoom
     */
    ResponseVO insertRoomInfo(HotelRoom hotelRoom);

    /**
     * 预订酒店后更新客房房间数量
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomInfo(Integer hotelId, String roomType, Integer rooms);

    /**
     * 获取酒店指定房间剩余数量
     * @param hotelId
     * @param roomType
     * @return
     */
    Integer getRoomCurNum(Integer hotelId, String roomType);

    /**
     * 删除房间
     * @param roomId
     * @return
     */
    ResponseVO delRoomInfo(Integer roomId);

    /**
      * @description: 更新房间总数
      * @param: roomId newRoomNum
      * @return:
      * @author: pkun
      * @date: 2020/6/26
      */
    ResponseVO updateRoomNum(Integer roomId, Integer newRoomNum);
}
