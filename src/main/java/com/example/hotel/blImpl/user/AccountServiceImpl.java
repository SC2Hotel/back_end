package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.CreditChangeReason;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.CreditChange;
import com.example.hotel.po.User;
import com.example.hotel.po.Vip;
import com.example.hotel.util.MD5Encryption;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private static final String ACCOUNT_EXIST = "账号已存在";
    private static final String UPDATE_ERROR = "修改失败";
    private static final Integer VIP_CREDIT = 600;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        // 初值设置为100
        user.setCredit(100.0);
        BeanUtils.copyProperties(userVO,user);
        user.setPassword(MD5Encryption.encrypt(userVO.getPassword()));
        try {
            User user1 = accountMapper.getAccountByEmail(userVO.getEmail());
            if(user1!=null){
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }
            accountMapper.createNewAccount(user);
            CreditChange creditChange = new CreditChange();
            creditChange.setChangeNum(100.0);
            creditChange.setCredit(100.0);
            creditChange.setOrderId(0);
            creditChange.setUserId(user.getId());
            creditChange.setReason(CreditChangeReason.init.toString());
            accountMapper.addCreditChange(creditChange);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseVO.buildFailure("注册失败");
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public User login(UserForm userForm) {
        User user = accountMapper.getAccountByEmail(userForm.getEmail());
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
            accountMapper.updateAccount(id, password.equals("")?"":MD5Encryption.encrypt(password), username, phonenumber);
        } catch (Exception e) {
            log.error(e.getMessage());
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
            if(vip.getType()==1){
                return ResponseVO.buildFailure("已经是普通会员");
            }else if(vip.getType()==2){
                return ResponseVO.buildFailure("已经企业会员");
            }else {
                return ResponseVO.buildFailure("error");
            }
        }
        User user = accountMapper.getAccountById(id);
        if(user.getCredit() < VIP_CREDIT){
            return ResponseVO.buildFailure(String.format("信用值低于%d，无法注册为会员", VIP_CREDIT));
        }
        Vip vipPO = new Vip();
        vipPO.setUserId(id);
        vipPO.setType(type);
        vipPO.setMessage(message);
        accountMapper.updateUserType(id, UserType.commonSeniorClient);
        return ResponseVO.buildSuccess(accountMapper.createNewVip(vipPO));
    }

    @Override
    public int updateUserCredit(CreditChange creditChange) {
        creditChange.setTime(new Timestamp(System.currentTimeMillis()));
        accountMapper.updateUserCredit(creditChange.getUserId(),-creditChange.getChangeNum());
        return accountMapper.addCreditChange(creditChange);
    }

    @Override
    public ResponseVO creditChange(int id) {
        return ResponseVO.buildSuccess(accountMapper.getAllUserCreditChange(id));
    }
}
