package com.example.hotel.data.hotel;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelAndRoomVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.updateHotelVO;
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

    int updateHotelInfo(updateHotelVO updateHotelVO);

    List<HotelVO> retrieveHotelsByHotelAndRoomVO(HotelAndRoomVO hotelAndRoomVO);
}
