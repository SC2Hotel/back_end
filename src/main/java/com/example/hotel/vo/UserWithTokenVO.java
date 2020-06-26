package com.example.hotel.vo;

import com.example.hotel.enums.UserType;
import lombok.Data;

/**
 * @author qin
 * @date 2020-06-22
 */
@Data
public class UserWithTokenVO {
    private Integer id;
    private String email;
    private String password;
    private String userName;
    private String phoneNumber;
    private Double credit;
    private UserType userType;
    private String njuToken;
    private String njuLongToken;
}
