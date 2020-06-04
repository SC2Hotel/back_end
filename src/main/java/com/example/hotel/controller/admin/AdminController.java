package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.blImpl.admin.AdminServiceImpl;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody UserForm userForm){

        return adminService.addManager(userForm);
    }

    @PostMapping("/getAllManagers")
    public ResponseVO getAllManagers(){
        return ResponseVO.buildSuccess(adminService.getAllManagers());
    }


    @GetMapping("/{hotelId}/getManager")
    public ResponseVO getHotelManager(@PathVariable int hotelId){
        try{
            return ResponseVO.buildSuccess(adminService.getHotelManager(hotelId));
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @GetMapping("/{type}/{information}/findUser")
    public ResponseVO findUser(@PathVariable Integer type, @PathVariable String information){
        try {
            return ResponseVO.buildSuccess(adminService.getUserByNameOrEmail(type, information));
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }
    @PostMapping("/updateUserInfo")
    public ResponseVO updateUserInfo(@RequestBody User user){
        try{
            return ResponseVO.buildSuccess(adminService.updateUserInformation(user));
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }
}
