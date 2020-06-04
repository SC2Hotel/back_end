package com.example.hotel.vo;

import com.example.hotel.enums.UserType;
import lombok.Data;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/6/4 9:30
 */
@Data
public class DisplayUserVO {

    private Integer id;
    private String email;
    private String userName;
    private String phoneNumber;
    private Double credit;
    private UserType userType;

}
