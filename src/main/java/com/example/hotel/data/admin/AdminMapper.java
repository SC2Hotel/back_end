package com.example.hotel.data.admin;

import com.example.hotel.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface AdminMapper {

    int addManager(User user);

    List<User> getAllManagers();

    User getAccountById(int userId);

    User getHotelManager(int hotelId);

    List<User> retrieveUserByName(String userName);

    List<User> retrieveUserByEmail(String email);

    int updateUserInfo(User user);

    int updateHelper(String password, int userId);

    void delAccountById(Integer userId);

    List<User> selectAllUsers();
}
