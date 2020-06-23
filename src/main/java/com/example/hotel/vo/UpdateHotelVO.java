package com.example.hotel.vo;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import lombok.Data;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/6/3 18:04
 */
@Data
public class UpdateHotelVO {

    private Integer id;
    private String address;
    private String bizRegion;
    private HotelStar hotelStar;
    private String description;
    private BizRegion bizRegionEnum;

}
