package com.example.hotel.data.hotel;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelAndRoomVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.UpdateHotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelMapper {

    int insertHotel(Hotel hotel);

    List<HotelVO> selectAllHotel();

    HotelVO selectById(@Param("id") Integer id);

    List<HotelVO> selectHotelByBizAndAdd(BizRegion bizRegion, String address);

    int updateHotelInfo(UpdateHotelVO updateHotelVO);

    List<HotelVO> retrieveHotelsByHotelAndRoomVO(HotelAndRoomVO hotelAndRoomVO);

    int deleteHotel(Integer hotelId);

    HotelVO selectByHotelManagerId(Integer id);

    void clearManager(Integer userId);

    int updateManager(Integer userId, Integer hotelId);

    List<HotelVO> selectHotelByPage(Integer startNum );
}
