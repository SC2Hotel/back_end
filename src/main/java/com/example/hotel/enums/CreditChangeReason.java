package com.example.hotel.enums;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/6/29 10:32
 */
public enum  CreditChangeReason {

    init("初始化"),
    executeOrder("执行订单"),
    annulOrder("撤销订单"),
    unCheckIn("未按时入住"),
    delayCheckIn("延时入住"),
    add("充值");

    private String value;

    CreditChangeReason(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
