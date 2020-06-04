package com.example.hotel.enums;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */
public enum UserType {
    Client("1"),
    HotelManager("2"),
    Manager("3"),
    commonSeniorClient("4"),
    companySeniorClient("5"),
    webPromotionStaff("6");
    private String value;

    UserType(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }

}
