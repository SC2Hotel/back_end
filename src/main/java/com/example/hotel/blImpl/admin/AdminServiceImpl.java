package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import com.example.hotel.util.RedisUtil;
import com.example.hotel.vo.DisplayUserVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    HotelMapper hotelMapper;
    @Autowired
    RedisUtil redisUtil;
    @Override
    public ResponseVO addManager(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUserType(UserType.HotelManager);
        try {
            adminMapper.addManager(user);
            //TODO 给相应的酒店设置管理员标记。如果酒店由管理员了，那就不能再添加管理员了
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<User> getAllManagers() {
        return adminMapper.getAllManagers();
    }

    @Override
    public User getHotelManager(int hotelId){
        if(redisUtil.hasKey(hotelId)){
            System.out.println("query from redis");
            return (User)redisUtil.get(hotelId);
        }
        User user = adminMapper.getHotelManager(hotelId);
        System.out.println("query from mysql");
        redisUtil.set(hotelId, user);
        return user;
    }

    @Override
    public List<User> getUserByNameOrEmail(Integer type, String information){
        //type = 1 邮箱 type = 0 名字
        List<User> users;
        if(type==1){
            users = adminMapper.retrieveUserByEmail(information);
            System.out.println("query from mysql");
        }else{
            users = adminMapper.retrieveUserByName(information);
        }
        return users;
    }

    @Override
    public int updateUserInformation(User user){
        return this.updateUserInfoHelper(user);
    }

    public int updateUserInfoHelper(User user){
        if(user.getPassword()!=null){
            return adminMapper.updateUserInfo(user)&adminMapper.updateHelper(user.getPassword(), user.getId());
        }
        else return adminMapper.updateUserInfo(user);
    }

}
