package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelAndRoomVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UpdateHotelVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
@Api(tags = "HotelController酒店相关接口")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;

    @ApiOperation("添加酒店")
    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {
        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }

    @ApiOperation("返回所有的酒店")
    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    @ApiOperation("添加酒店住房")
    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess("添加成功");
    }

    @ApiOperation("删除酒店住房")
    @PostMapping("/delRoom/{roomId}")
    public ResponseVO delRoomInfo(@PathVariable Integer roomId) {
        return roomService.delRoomInfo(roomId);
    }

    @ApiOperation("根据hotelId返回酒店信息")
    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    @ApiOperation("根据商圈和地址搜索")
    @GetMapping("/search/{bizRegion}")
    public ResponseVO retrieveHotelsByBizAndAdd(@PathVariable String bizRegion,@RequestParam String address){
        return ResponseVO.buildSuccess(hotelService.retrieveHotelsByBizAndAdd(bizRegion,address));
    }

    @ApiOperation("返回所有商圈")
    @GetMapping("/bizRegions")
    public ResponseVO retrieveAllBizRegions(){
        return ResponseVO.buildSuccess(hotelService.retrieveAllBizRegions());
    }

    @ApiOperation("根据酒店和房间信息查询酒店")
    @PostMapping("/search")
    public ResponseVO retrieveHotelsByHotelAndRoomVO(@RequestBody HotelAndRoomVO hotelAndRoomVO){
        return ResponseVO.buildSuccess(hotelService.retrieveHotelsByHotelAndRoomVO(hotelAndRoomVO));
    }

    @ApiOperation("更新酒店信息")
    @PostMapping("/update")
    public ResponseVO updateHotelInfo(@RequestBody UpdateHotelVO updateHotelVO){
        return hotelService.updateHotelInfo(updateHotelVO);
    }

    @ApiOperation("根据酒店管理员ID获取酒店信息")
    @GetMapping("/{hotelManagerId}/getHotel")
    public ResponseVO getHotelByManager(@PathVariable("hotelManagerId") int hotelManagerId){
        try{
            return ResponseVO.buildSuccess(hotelService.getHotelByManager(hotelManagerId));
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @ApiOperation("删除酒店")
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
