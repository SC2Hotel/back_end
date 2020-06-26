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
import com.example.hotel.util.DateTimeUtil;
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

import static com.example.hotel.util.RedisUtil.HOTEL_KEY_NAME_PREFIX;
import static com.example.hotel.util.RedisUtil.ROOM_KEY_NAME_PREFIX;

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
        hotel.setBizRegion(stringToBizRegion(hotelVO.getBizRegion()));
        hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {

        Integer curNum = roomService.getRoomCurNum(hotelId,roomType);
        if(curNum < rooms) { // 目前的房间 小于 要减去的房间
            // error
        }
        roomService.updateRoomInfo(hotelId,roomType,rooms);
        redisUtil.delete(ROOM_KEY_NAME_PREFIX +hotelId);

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
        return Arrays.stream(regions).map(BizRegion::toString).collect(Collectors.toList());
    }

    @Override
    public List<HotelVO> retrieveHotels() {
        return hotelMapper.selectAllHotel();
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        HotelVO hotelVO;
        if(redisUtil.hasKey(HOTEL_KEY_NAME_PREFIX + hotelId)){
            hotelVO = (HotelVO)redisUtil.get(HOTEL_KEY_NAME_PREFIX + hotelId);
        }
        else{
            hotelVO = hotelMapper.selectById(hotelId);
            redisUtil.set(HOTEL_KEY_NAME_PREFIX + hotelId, hotelVO);
            redisUtil.expire(HOTEL_KEY_NAME_PREFIX + hotelId, DateTimeUtil.TWO_HOURS_IN_SECOND);
        }

        List<HotelRoom> rooms;
        if(redisUtil.hasKey(ROOM_KEY_NAME_PREFIX +hotelId)){
            rooms = (List<HotelRoom>)redisUtil.get(ROOM_KEY_NAME_PREFIX +hotelId);
        }
        else{
            rooms = roomService.retrieveHotelRoomInfo(hotelId);
            redisUtil.set(ROOM_KEY_NAME_PREFIX +hotelId, rooms);
            redisUtil.expire(ROOM_KEY_NAME_PREFIX + hotelId, DateTimeUtil.TWO_HOURS_IN_SECOND);
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
        if(redisUtil.hasKey(HOTEL_KEY_NAME_PREFIX +updateHotelVO.getId())){
            redisUtil.delete(HOTEL_KEY_NAME_PREFIX +updateHotelVO.getId());
        }
        try{
            updateHotelVO.setBizRegionEnum(stringToBizRegion(updateHotelVO.getBizRegion()));
            hotelMapper.updateHotelInfo(updateHotelVO);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @Override
    public List<HotelVO> retrieveHotelsByHotelAndRoomVO(HotelAndRoomVO hotelAndRoomVO) {
        String bizRegionStr = hotelAndRoomVO.getBizRegion();
        hotelAndRoomVO.setBizRegionEnum(stringToBizRegion(bizRegionStr));
        return hotelMapper.retrieveHotelsByHotelAndRoomVO(hotelAndRoomVO);
    }

    @Override
    public HotelVO getHotelByManager(Integer hotelManagerId) {
        return hotelMapper.selectByHotelManagerId(hotelManagerId);
    }

    @Override
    public int deleteHotel(Integer hotelId) {
        if(redisUtil.hasKey(HOTEL_KEY_NAME_PREFIX + hotelId)){
            redisUtil.delete(HOTEL_KEY_NAME_PREFIX + hotelId);
        }
        return hotelMapper.deleteHotel(hotelId);
    }
}
