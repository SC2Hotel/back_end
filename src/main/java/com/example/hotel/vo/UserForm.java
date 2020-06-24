package com.example.hotel.vo;

import lombok.Data;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Data
public class UserForm {
    /**
     * 用户邮箱，不可重复
     */
    private String email;
    /**
     * 用户密码
     */
    private String password;
}
