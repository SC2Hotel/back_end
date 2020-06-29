package com.example.hotel.po;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/6/29 10:35
 */
@Data
public class CreditChange {

    private Integer id;
    private Integer userId;
    private String reason;
    private Double changeNum;
    private Integer orderId;
    private Double credit;
    private Timestamp time;
}
