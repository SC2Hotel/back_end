package com.example.hotel.data.user;

import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



@Mapper
@Repository
public interface AccountMapper {

    /**
     * 创建一个新的账号
     *
     * @return
     */
     int createNewAccount(User user);

    /**
     * 根据用户名查找账号
     * @param email
     * @return
     */
     User getAccountByName(@Param("email") String email);

     User getAccountById(@Param("id") int id);

    /**
     * 更新用户信息
     * @param id
     * @param password
     * @param username
     * @param phonenumber
     * @return
     */
     int updateAccount(@Param("id") int id, @Param("password") String password,@Param("userName") String username, @Param("phoneNumber") String phonenumber);


    /**
     * 更新 减少用户的信用值
     * @param id 用户id
     * @param creditToMinus 需要减少的信用值
     * @return
     */
    int updateUserCredit(@Param("id") Integer id,@Param("creditToMinus") Double creditToMinus);

    /**
     * 根据用户id获取会员信息
     * @param id 用户id
     * @return
     */
    Vip getVipById(int id);

    int createNewVip(Vip vip);
}