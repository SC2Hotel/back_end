package com.example.hotel.data.user;

import com.example.hotel.po.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author qin
 * @date 2020-05-08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccountMapperTest {

    // TABLE `User` (`id` ,`email` ,`password` , `username` , `phonenumber` , `credit` , `usertype`
    //INSERT INTO `User` VALUES (4,'1012681@qq.com','123456','测试一号','12345678919',100,'Client'),
    // (5,'123@qq.com','123456','测试二号','12345678911',100,'Client'),
    // (6,'333@qq.com','123456',NULL,NULL,NULL,'HotelManager');
    @Autowired
    AccountMapper accountMapper;
    @Test
    public void createNewAccount() {
        User user = new User();
        user.setUserName("Test");
        user.setEmail("*"+System.currentTimeMillis()/1000+"@qq.com");
        user.setPassword("123456");
        int row = accountMapper.createNewAccount(user);
        Assert.assertEquals(1,row);
    }

    @Test
    public void getAccountByName() {
        User user = accountMapper.getAccountByName("123@qq.com");
        Assert.assertEquals("测试二号",user.getUserName());
    }

    @Test
    public void getAccountById() {
        User user = accountMapper.getAccountById(4);
        Assert.assertEquals("测试一号",user.getUserName());
    }

    @Test
    public void updateAccount() {
        int row = accountMapper.updateAccount(6,"123456","管理员","12345678910");
        Assert.assertEquals(1,row);
    }
}