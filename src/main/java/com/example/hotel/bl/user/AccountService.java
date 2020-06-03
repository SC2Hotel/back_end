package com.example.hotel.bl.user;

import com.example.hotel.po.User;
import com.example.hotel.vo.*;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     *
     * @return
     */
    ResponseVO registerAccount(UserVO userVO);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     *
     * @return
     */
    User login(UserForm userForm);

    /**
     * 获取用户个人信息
     * @param id
     * @return
     */
    User getUserInfo(int id);

    /**
     * 更新用户个人信息
     * @param id
     * @param password
     * @param username
     * @param phonenumber
     * @return
     */
    ResponseVO updateUserInfo(int id, String password,String username,String phonenumber);

    /**
     * 注册会员
     * @param id 用户id
     * @param type 1 表示普通会员 ； 2表示企业会员
     * @param message 普通会员 登记生日 'yyyy-MM-hh'格式字符串 ； 企业会员 登记企业名
     * @return
     */
    ResponseVO registerSenior(int id,int type,String message);

    int updateUserCredit(Integer userId, Double creditToMinus);
}
