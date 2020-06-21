package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelAndRoomVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UpdateHotelVO;
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

    @PostMapping("/search")
    public ResponseVO retrieveHotelsByHotelAndRoomVO(@RequestBody HotelAndRoomVO hotelAndRoomVO){
        return ResponseVO.buildSuccess(hotelService.retrieveHotelsByHotelAndRoomVO(hotelAndRoomVO));
    }

    @PostMapping("/update")
    public ResponseVO updateHotelInfo(@RequestBody UpdateHotelVO updateHotelVO){
        return hotelService.updateHotelInfo(updateHotelVO);
    }

    @PostMapping("/{hotelId}/delete")
    public ResponseVO deleteHotel(@PathVariable("hotelId") Integer hotelId){
        int res = -1;
        try{
            res = hotelService.deleteHotel(hotelId);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            if(res == -1){
                return ResponseVO.buildFailure("数据库出错");
            }else if(res == 0){
                return ResponseVO.buildFailure("hotelId不存在");
            }
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

}
