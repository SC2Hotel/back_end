package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.User;
import com.example.hotel.util.RedisUtil;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelAndRoomVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.RoomVO;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoomService roomService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public void addHotel(HotelVO hotelVO) throws ServiceException {
        User manager = accountService.getUserInfo(hotelVO.getManagerId());
        if(manager == null || !manager.getUserType().equals(UserType.HotelManager)){
            throw new ServiceException("管理员不存在或者无权限！创建酒店失败！");
        }
        Hotel hotel = new Hotel();
        hotel.setDescription(hotelVO.getDescription());
        hotel.setAddress(hotelVO.getAddress());
        hotel.setHotelName(hotelVO.getName());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
        hotel.setManagerId(hotelVO.getManagerId());
        hotel.setRate(hotelVO.getRate());
        hotel.setBizRegion(BizRegion.valueOf(hotelVO.getBizRegion()));
        hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomService.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public Integer getRoomCurNum(Integer hotelId, String roomType) {
        return roomService.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public List<HotelVO> retrieveHotelsByBizAndAdd(String bizRegion, String address) {

        return hotelMapper.selectHotelByBizAndAdd(stringToBizRegion(bizRegion),address);
    }

    private BizRegion stringToBizRegion(String bizRegionStr){
        List<BizRegion> bizRegions = retrieveAllBizRegionsOri();
        for(BizRegion bizRegion:bizRegions){
            if(bizRegionStr.equals(bizRegion.toString())){
                return bizRegion;
            }
        }
        return null;
    }

    private List<BizRegion> retrieveAllBizRegionsOri() {
        BizRegion[] regions = BizRegion.values();
        List<BizRegion> bizRegions = new ArrayList<>();
        Collections.addAll(bizRegions,regions);
        return bizRegions;
    }

    @Override
    public List<String> retrieveAllBizRegions() {
        BizRegion[] regions = BizRegion.values();
        return Arrays.stream(regions).map((x)->x.toString()).collect(Collectors.toList());
    }

    @Override
    public List<HotelVO> retrieveHotels() {

        return hotelMapper.selectAllHotel();
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        HotelVO hotelVO;
        if(redisUtil.hasKey(hotelId+"Hotel")){
            hotelVO = (HotelVO)redisUtil.get(hotelId+"Hotel");
        }
        else{
            hotelVO = hotelMapper.selectById(hotelId);
            redisUtil.set(hotelId+"Hotel", hotelVO);
        }

        List<HotelRoom> rooms;
        if(redisUtil.hasKey(hotelId+"Room")){
            rooms = (List<HotelRoom>)redisUtil.get(hotelId+"Room");
        }
        else{
            rooms = roomService.retrieveHotelRoomInfo(hotelId);
            redisUtil.set(hotelId+"Room", rooms);
        }

        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);

        return hotelVO;
    }

    @Override
    public ResponseVO updateHotelInfo(UpdateHotelVO updateHotelVO){
        if(redisUtil.hasKey(updateHotelVO.getId()+"Hotel")){
            redisUtil.delete(updateHotelVO.getId()+"Hotel");
        }
        try{
            hotelMapper.updateHotelInfo(updateHotelVO);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @Override
    public List<HotelVO> retrieveHotelsByHotelAndRoomVO(HotelAndRoomVO hotelAndRoomVO) {
        // todo 将枚举字符串转换为对应的枚举值
        return hotelMapper.retrieveHotelsByHotelAndRoomVO(hotelAndRoomVO);
    }

}
