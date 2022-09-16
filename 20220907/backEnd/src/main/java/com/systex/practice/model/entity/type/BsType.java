package com.systex.practice.model.entity.type;

public enum BsType {

    B("買進"), S("賣出");

    private String chinese;

    BsType(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return this.chinese;
    }
}
