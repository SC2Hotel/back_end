package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
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
 * @date 2020-06-15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminServiceTest {

    @Autowired
    AdminService adminService;
    @Test
    public void addManager() {
        //todo
//        UserForm userForm = new UserForm();
//        userForm
//        adminService.addManager();
    }

    @Test
    public void getAllManagers() {
        List<User> managers = adminService.getAllManagers();
        Assert.assertTrue(managers.size() > 0);
    }

    @Test
    public void getHotelManager1() {
        User hotelManager = adminService.getHotelManager(3);
        Assert.assertNotNull(hotelManager);
    }

    @Test
    public void getHotelManager2() {
        User hotelManager = adminService.getHotelManager(-1);
        Assert.assertNull(hotelManager);
    }

    @Test
    public void getUserByNameOrEmail1() {
        List<User> users = adminService.getUserByNameOrEmail(0, "");
        Assert.assertTrue(users.size() > 0);
    }

    @Test
    public void getUserByNameOrEmail2() {
        List<User> users = adminService.getUserByNameOrEmail(1,"123@qq.com");
        Assert.assertTrue(users.size() > 0);
    }

    @Test
    public void updateUserInformation() {
        User user = adminService.getHotelManager(3);
        user.setCredit(500);
        int row = adminService.updateUserInformation(user);
        assertEquals(1, row);
    }
}