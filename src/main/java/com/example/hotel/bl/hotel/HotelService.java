package com.example.hotel.bl.hotel;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelAndRoomVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UpdateHotelVO;

import java.util.List;

public interface HotelService {

    /**
     * 添加酒店
     * @param hotelVO
     * @throws
     */
    void addHotel(HotelVO hotelVO) throws ServiceException;


    /**
     * 预订酒店修改剩余客房信息
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomInfo(Integer hotelId, String roomType,Integer rooms);

    /**
     * 列表获取酒店信息
     * @return
     */
    List<HotelVO> retrieveHotels();

    /**
     * 获取某家酒店详细信息
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 查看酒店剩余某种房间数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId,String roomType);

    /**
     * 根据商圈和地址查找酒店
     * @param bizRegion 商圈
     * @param address 地址
     * @return
     */
    List<HotelVO> retrieveHotelsByBizAndAdd(String bizRegion, String address);

    /**
     * 查找所有商区
     * @return
     */
    List<BizRegion> retrieveAllBizRegions();

    /**
     * 搜索酒店信息
     * @param hotelAndRoomVO 要搜索的酒店信息
     * @return
     */
    List<HotelVO> retrieveHotelsByHotelAndRoomVO(HotelAndRoomVO hotelAndRoomVO);

    /**
     * 更新酒店的信息
     * @return
     */
    ResponseVO updateHotelInfo(UpdateHotelVO updateHotelVO);

}
