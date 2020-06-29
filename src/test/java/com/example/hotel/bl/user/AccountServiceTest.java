package com.example.hotel.bl.user;

import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.CreditChangeReason;
import com.example.hotel.po.CreditChange;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * @author qin
 * @date 2020-06-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    AccountService accountService;
    @Test
    public void registerAccount() {
        UserVO userVO = new UserVO();
        userVO.setEmail("test@qq.com");
        userVO.setUserName("test");
        userVO.setPassword("123456");
        userVO.setCredit(500.0);
        ResponseVO responseVO = accountService.registerAccount(userVO);
        assertTrue(responseVO.getSuccess());
    }

    @Test
    public void login() {
        UserForm userForm = new UserForm();
        userForm.setEmail("333@qq.com");
        userForm.setPassword("123456");
        User user = accountService.login(userForm);
        assertEquals("333@qq.com",user.getEmail());
    }

    @Test
    public void getUserInfo() {
        User userInfo = accountService.getUserInfo(4);
        assertEquals("1012681@qq.com",userInfo.getEmail());
    }

    @Test
    public void updateUserInfo() {
        ResponseVO responseVO = accountService.updateUserInfo(4, "123456", "测试", "15521210202");
        assertTrue(responseVO.getSuccess());
        accountService.updateUserInfo(4, "123456", "测试一号", "15521210202");
    }

    @Test
    public void registerSenior() {
        ResponseVO responseVO = accountService.registerSenior(4, 1, "2000-06-15");
        // 已注册普通会员
        assertFalse(responseVO.getSuccess());
    }

    @Test
    public void updateUserCredit() {
        CreditChange creditChange = new CreditChange();
        creditChange.setUserId(1);
        creditChange.setChangeNum(100.0);
        creditChange.setCredit(100.0);
        creditChange.setOrderId(2);
        creditChange.setReason(CreditChangeReason.init.toString());
        int row = accountService.updateUserCredit(creditChange);
        Assert.assertTrue(1 == row);
    }
}