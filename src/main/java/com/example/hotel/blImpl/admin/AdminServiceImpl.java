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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String MANAGER_EXIST = "该酒店已经存在管理员";
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    HotelMapper hotelMapper;
    @Override
    public ResponseVO addManager(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUserType(UserType.HotelManager);
        try {
            if(hotelMapper.selectById(userForm.getHotelId()).getManagerId()!=null){
                return ResponseVO.buildFailure(MANAGER_EXIST);
            }
            adminMapper.addManager(user);
            hotelMapper.updateManager(user.getId(), userForm.getHotelId());
        } catch (Exception e) {
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
        return adminMapper.getHotelManager(hotelId);
    }

    @Override
    public List<User> getUserByNameOrEmail(Integer type, String information){
        //type = 1 邮箱 type = 0 名字
        List<User> users;
        if(type==1){
            users = adminMapper.retrieveUserByEmail(information);
        }else{
            users = adminMapper.retrieveUserByName(information);
        }
        return users;
    }

    @Override
    public int updateUserInformation(User user){
        return this.updateUserInfoHelper(user);
    }

    @Override
    @Transactional
    public ResponseVO delUser(Integer userId) {
        if(adminMapper.getAccountById(userId)==null){
            return ResponseVO.buildFailure(MANAGER_EXIST);
        }
        try{
            adminMapper.delAccountById(userId);
            hotelMapper.clearManager(userId);
            return ResponseVO.buildSuccess("删除管理员成功");
        }catch (Exception e){
            return ResponseVO.buildFailure("删除管理员失败");
        }
    }

    public int updateUserInfoHelper(User user){
        if(user.getPassword()!=null){
            return adminMapper.updateUserInfo(user)&adminMapper.updateHelper(user.getPassword(), user.getId());
        }
        else return adminMapper.updateUserInfo(user);
    }

}
