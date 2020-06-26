package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface AdminService {

    /**
     * 添加酒店管理人员账号
     * @param userForm
     * @return
     */
    ResponseVO addManager(UserForm userForm);

    /**
     * 获得所有酒店管理人员信息
     * @return
     */
    List<User> getAllManagers();

    /**
      * @description: 获得一个酒店的管理人员信息
      * @param: int hotelId
      * @return: User
      * @author: pkun
      * @date: 2020/6/4
      */
    User getHotelManager(int hotelId);

    /**
      * @description: 根据用户的名字或者邮箱来查找用户信息
      * @param: type = 1 邮箱 type = 0 名字
      * @return: User
      * @author: pkun
      * @date: 2020/6/4
      */
    List<User> getUserByNameOrEmail(Integer type, String information);

    /**
      * @description: 更改用户信息
      * @param: User
      * @return: 1 成功 0 失败
      * @author: pkun
      * @date: 2020/6/4
      */
    int updateUserInformation(User user);

    /**
     * @description: 更改用户信息
     * @param: userId
     * @return: 操作结果
     * @author: lzh
     * @date: 2020/6/24
     */
    ResponseVO delUser(Integer userId);


    /**
      * @description: 重置密码
      * @param: userId
      * @return: ResponseVO
      * @author: pkun
      * @date: 2020/6/25
      */
    ResponseVO resetPassword(Integer userId);


    /**
      * @description: 获取所有用户的信息
      * @param:
      * @return: List\<user\>
      * @author: pkun
      * @date: 2020/6/26
      */
    List<User> getAllUsers();
}
