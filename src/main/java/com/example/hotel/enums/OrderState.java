package com.example.hotel.enums;

/**
 * @author pkun
 * @version 1.0
 * @date 2020/5/16 12:12
 */
public enum OrderState {
    /**
     * booked -> 预定后设置的状态
     * withdrawn -> 撤销订单后的状态
     * exception -> 订单发生异常，时间在超过最晚订单执行时间后还没有办理入住
     * execute -> 订单正常结束
     */
    Booked("已预订"),
    Withdrawn("已撤销"),
    exception("异常"),
    execute("已执行"),
    evaluation("已评价");
    private String value;

    OrderState(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
