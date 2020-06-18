package com.example.hotel.data.admin;

import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @author qin
 * @date 2020-05-23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminMapperTest {
    @Autowired
    AdminMapper adminMapper;

    @Test
    public void Test01addManager() {
        User user = new User();
        user.setEmail("*" + System.currentTimeMillis() / 10000 + "@qq.com");
        user.setPassword("123456");
        user.setUserType(UserType.HotelManager);
        int row = adminMapper.addManager(user);
        Assert.assertEquals(1, row);
    }

    @Test
    public void Test02getAllManagers() {
        List<User> lists = adminMapper.getAllManagers();
        Assert.assertTrue(lists.size() >= 1);
    }

    @Test
    public void Test03getAccountById(){
        //5	123@qq.com	123456	测试二号	12345678911	100	HotelManager
        User user = adminMapper.getAccountById(5);
        Assert.assertEquals(user.getEmail(), "123@qq.com");
        Assert.assertEquals(user.getPassword(), "123456");
        Assert.assertEquals(user.getUserType(), UserType.HotelManager);
    }

    @Test
    public void Test04getHotelManager(){
        User user = adminMapper.getHotelManager(4);
        Assert.assertEquals(user.getUserType(), UserType.HotelManager);
        Assert.assertEquals(user.getEmail(), "123@qq.com");
        Assert.assertEquals(user.getPassword(), "123456");
    }

    @Test
    public void Test05retrieveUserByName(){
        List<User> users = adminMapper.retrieveUserByName("测试");
        Assert.assertEquals(users.get(0).getUserName(), "测试一号");
        Assert.assertEquals(users.get(1).getUserName(), "测试二号");
    }

    @Test
    public void Test06retrieveUserByEmail(){
        List<User> users = adminMapper.retrieveUserByEmail("123@qq.com");
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(users.get(0).getUserName(), "测试二号");
    }

    @Test
    public void Test07updateUserInfo(){
        User user = new User();
        user.setId(15);
        user.setCredit(1.0);
        user.setUserType(UserType.HotelManager);
        user.setPassword("1234567");
        adminMapper.updateUserInfo(user);
        adminMapper.updateHelper(user.getPassword(), user.getId());
        User test = adminMapper.getAccountById(15);
        Assert.assertEquals(test.getUserType(), user.getUserType());
        Assert.assertEquals(test.getPassword(), user.getPassword());
        user.setCredit(0.0);
        user.setPassword("123456");
        user.setUserType(UserType.Client);
        adminMapper.updateUserInfo(user);
    }
}