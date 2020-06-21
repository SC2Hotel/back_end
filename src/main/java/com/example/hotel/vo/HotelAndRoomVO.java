package com.example.hotel.vo;

import com.example.hotel.enums.BizRegion;
import lombok.Data;

/**
 * @author qin
 * @date 2020-06-03
 */
@Data
public class HotelAndRoomVO {
    private String bizRegion;
    private String address;
    private String name;
    private BizRegion bizRegionEnum;

    private String roomType;
    private Double loPrice;
    private Double hiPrice;
    private String hotelStar;   // 酒店星级
    private Double rate;        // 酒店最低评分

}
