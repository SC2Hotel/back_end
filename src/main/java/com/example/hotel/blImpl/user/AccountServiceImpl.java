package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.util.MD5Encryption;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String UPDATE_ERROR = "修改失败";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        user.setCredit(100.0); // 初值设置为100
        BeanUtils.copyProperties(userVO,user);
        user.setPassword(MD5Encryption.encrypt(userVO.getPassword()));
        try {
            //TODO createNewAccount不会触发账号已存在，需要检查邮箱是否重复
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public User login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getEmail());
        if (null == user || !user.getPassword().equals(MD5Encryption.encrypt(userForm.getPassword()))) {
            return null;
        }
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public ResponseVO updateUserInfo(int id, String password, String username, String phonenumber) {
        try {
            accountMapper.updateAccount(id, MD5Encryption.encrypt(password), username, phonenumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO registerSenior(int id,int type,String message){
        if(type!=1&&type!=2){
            return ResponseVO.buildFailure("参数错误，type为 1 或 2");
        }
        Vip vip = accountMapper.getVipById(id);
        if(vip!=null){
            //TODO 改成枚举值，我把枚举值的value修改成了Integer
            if(vip.getType()==1){
                return ResponseVO.buildFailure("已经是普通会员");
            }else if(vip.getType()==2){
                return ResponseVO.buildFailure("已经企业会员");
            }else {
                return ResponseVO.buildFailure("error");
            }
        }
        Vip vipPO = new Vip();
        vipPO.setUserId(id);
        vipPO.setType(type);
        vipPO.setMessage(message);
        return ResponseVO.buildSuccess(accountMapper.createNewVip(vipPO));
    }

    @Override
    public int updateUserCredit(Integer userId, Double creditToMinus) {
        return accountMapper.updateUserCredit(userId,creditToMinus);
    }
}
