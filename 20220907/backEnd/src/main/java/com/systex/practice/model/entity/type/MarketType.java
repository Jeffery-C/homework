package com.systex.practice.model.entity.type;

public enum MarketType {

    T("上市"), O("上櫃"), R("興櫃");

    private String chinese;

    MarketType(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return this.chinese;
    }
}
