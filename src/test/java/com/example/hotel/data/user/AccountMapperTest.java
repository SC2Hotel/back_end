package com.example.hotel.data.user;

import com.example.hotel.enums.CreditChangeReason;
import com.example.hotel.po.CreditChange;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Currency;
import java.util.List;

import static com.example.hotel.enums.UserType.HotelManager;
import static com.example.hotel.enums.UserType.commonSeniorClient;

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
        user.setPhoneNumber("123123123");
        user.setCredit(10000.0);
        user.setUserType(HotelManager);
        int row = accountMapper.createNewAccount(user);
        Assert.assertEquals(1,row);
    }

    @Test
    public void getAccountByName() {
        User user = accountMapper.getAccountByEmail("123@qq.com");
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

    @Test
    public void updateUserCredit(){
        accountMapper.updateUserCredit(5, 50.0);
        User user = accountMapper.getAccountById(5);
        Assert.assertTrue(user.getCredit()==50.0);
        accountMapper.updateUserCredit(5, -50.0);
    }

    @Test
    public void getVipById(){
        Vip vip = accountMapper.getVipById(4);
        Assert.assertNotNull(vip);
    }

    @Test
    public void createNewVip(){
        Vip vip = new Vip();
        vip.setType(commonSeniorClient.getValue());
        vip.setUserId(13);
        vip.setMessage("测试");
        try{
            int ret = accountMapper.createNewVip(vip);
            Assert.assertTrue(ret == 1);
        }catch (Exception e){
            Vip vip1 = accountMapper.getVipById(13);
            Assert.assertNotNull(vip1);
        }
    }

    @Test
    public void updateUserType(){
        accountMapper.updateUserType(4, commonSeniorClient);
        User user = accountMapper.getAccountById(4);
        Assert.assertEquals(user.getUserType(), commonSeniorClient);
    }

    @Test
    public void addCreditChange(){
        CreditChange creditChange = new CreditChange();
        creditChange.setUserId(1);
        creditChange.setTime(new Timestamp(System.currentTimeMillis()));
        creditChange.setChangeNum(100.0);
        creditChange.setCredit(100);
        creditChange.setOrderId(2);
        creditChange.setReason(CreditChangeReason.init.toString());
        Assert.assertTrue(1 == accountMapper.addCreditChange(creditChange));
    }

    @Test
    public void getAllUserCreditChange(){
        List<CreditChange> creditChanges = accountMapper.getAllUserCreditChange(1);
        Assert.assertEquals(CreditChangeReason.init.toString(), creditChanges.get(0).getReason());
    }
}
