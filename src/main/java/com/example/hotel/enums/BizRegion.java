package com.example.hotel.enums;

public enum BizRegion {
    XiDan("西单"),
    WangFuJing("王府井"),
    XinJieKou("新街口"),
    FuZiMiao("夫子庙"),
    ZhuJiangXinCheng("珠江新城"),
    BeiJingLu("北京路");

    private String value;

    BizRegion(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
