package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.blImpl.admin.AdminServiceImpl;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.xml.ws.Response;
import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@RestController()
@RequestMapping("/api/admin")
@Api(tags = "AdminController管理员相关接口")
@Slf4j
public class AdminController {
    @Autowired
    AdminService adminService;

    @ApiOperation("添加酒店管理员")
    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody UserForm userForm){
        return adminService.addManager(userForm);
    }

    @ApiOperation("获取所有的管理员")
    @PostMapping("/getAllManagers")
    public ResponseVO getAllManagers(){
        return ResponseVO.buildSuccess(adminService.getAllManagers());
    }

    @ApiOperation("获取某个酒店的管理员信息")
    @GetMapping("/{hotelId}/getManager")
    public ResponseVO getHotelManager(@PathVariable int hotelId){
        try{
            return ResponseVO.buildSuccess(adminService.getHotelManager(hotelId));
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @ApiOperation("通过邮箱或者用户名查找到用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "type = 1 邮箱 type = 0 名字"),
            @ApiImplicitParam(name = "information", value = "邮箱或者名字")
    })
    @GetMapping("/{type}/{information}/findUser")
    public ResponseVO findUser(@PathVariable Integer type, @PathVariable String information){
        try {
            return ResponseVO.buildSuccess(adminService.getUserByNameOrEmail(type, information));
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/updateUserInfo")
    public ResponseVO updateUserInfo(@RequestBody User user){
        try{
            return ResponseVO.buildSuccess(adminService.updateUserInformation(user));
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @ApiOperation("删除管理员并更新酒店信息")
    @PostMapping("/delUser/{userId}")
    public ResponseVO delUser(@PathVariable Integer userId){
        try{
            return adminService.delUser(userId);
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @ApiOperation("重置密码")
    @PostMapping("/{userId}/resetPassword")
    public ResponseVO resetPassword(@PathVariable Integer userId){
        return adminService.resetPassword(userId);
    }

    @ApiOperation("获取所有用户的信息")
    @PostMapping("/getAllUsersInfo")
    public ResponseVO getAllUsersInfo(){
        try{
            return ResponseVO.buildSuccess(adminService.getAllUsers());
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseVO.buildFailure("查询失败");
        }
    }
}
