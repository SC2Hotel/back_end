package com.example.hotel.controller.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import com.example.hotel.util.JwtUtil;
import com.example.hotel.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController()
@RequestMapping("/api/user")
@Api(tags = "AccountController用户相关接口")
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
    @Autowired
    private AccountService accountService;

    private Integer shortTokenTime = 20;

    private Integer longTokenTime = 60;

    @ApiOperation("登陆")
    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm) {
        User user = accountService.login(userForm);
        if (user == null) {
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        UserWithTokenVO userWithToken = new UserWithTokenVO();
        BeanUtils.copyProperties(user,userWithToken);
        //短token时长20min 长token时长60min
        userWithToken.setNjuToken(JwtUtil.createToken(user.getId(),shortTokenTime));
        userWithToken.setNjuLongToken(JwtUtil.createToken(user.getId(),longTokenTime));
        return ResponseVO.buildSuccess(userWithToken);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserVO userVO) {
        return accountService.registerAccount(userVO);
    }


    @ApiOperation("获取某个用户信息")
    @GetMapping("/{id}/getUserInfo")
    public ResponseVO getUserInfo(@PathVariable int id, HttpServletRequest request) {
        String token = request.getHeader(JwtUtil.TOKEN_NAME);
        Integer userId = JwtUtil.verifyTokenAndGetUserId(token);
        if(userId==null || id!=userId){
            return ResponseVO.buildFailure("用户id错误");
        }
        User user = accountService.getUserInfo(id);
        if(user==null){
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(user);
    }

    @ApiOperation("更新某个用户的信息")
    @PostMapping("/{id}/userInfo/update")
    public ResponseVO updateInfo(@RequestBody UserInfoVO userInfoVO,@PathVariable int id, HttpServletRequest request){
        String token = request.getHeader(JwtUtil.TOKEN_NAME);
        Integer userId = JwtUtil.verifyTokenAndGetUserId(token);
        if(userId==null || id!=userId){
            return ResponseVO.buildFailure("用户id错误");
        }
        return accountService.updateUserInfo(id,userInfoVO.getPassword(),userInfoVO.getUserName(),userInfoVO.getPhoneNumber());
    }

    @ApiOperation("让某个用户注册为会员")
    @PostMapping("/{id}/registerSenior")
    public ResponseVO registerSenior(@PathVariable int id,@RequestParam int type,@RequestParam String message){
        return accountService.registerSenior(id,type,message);
    }

    @ApiOperation("获取某个用户信用值变化记录")
    @PostMapping("/{id}/creditChange")
    public ResponseVO creditChange(@PathVariable int id){
        return accountService.creditChange(id);

    }
}
