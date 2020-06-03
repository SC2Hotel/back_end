package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.updateHotelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;


    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {

        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess();
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    @GetMapping("/search/{bizRegion}")
    public ResponseVO retrieveHotelsByBizAndAdd(@PathVariable String bizRegion,@RequestParam String address){
        return ResponseVO.buildSuccess(hotelService.retrieveHotelsByBizAndAdd(bizRegion,address));
    }

    @GetMapping("/bizRegions")
    public ResponseVO retrieveAllBizRegions(){
        return ResponseVO.buildSuccess(hotelService.retrieveAllBizRegions());
    }

    @PostMapping("/update")
    public ResponseVO updateHotelInfor(@RequestBody updateHotelVO updateHotelVO){
        return hotelService.updateHotelInfor(updateHotelVO);
    }

}
