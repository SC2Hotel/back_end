package com.example.hotel.enums;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */
public enum UserType {
    Client(1),
    HotelManager(2),
    Admin(3),
    commonSeniorClient(4),
    companySeniorClient(5),
    webPromotionStaff(6);
    private Integer value;

    UserType(Integer value) {
        this.value = value;
    }


    public Integer getValue() {
        return value;
    }
}
