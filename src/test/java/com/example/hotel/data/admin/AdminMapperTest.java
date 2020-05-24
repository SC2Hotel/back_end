package com.example.hotel.data.admin;

import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author qin
 * @date 2020-05-23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminMapperTest {
    @Autowired
    AdminMapper adminMapper;

    @Test
    public void addManager() {
        User user = new User();
        user.setEmail("*" + System.currentTimeMillis() / 10000 + "@qq.com");
        user.setPassword("123456");
        user.setUserType(UserType.HotelManager);
        int row = adminMapper.addManager(user);
        Assert.assertEquals(1, row);
    }

    @Test
    public void getAllManagers() {
        List<User> lists = adminMapper.getAllManagers();
        Assert.assertTrue(lists.size() >= 1);
    }
}