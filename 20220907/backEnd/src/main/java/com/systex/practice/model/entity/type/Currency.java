package com.systex.practice.model.entity.type;

public enum Currency {

    NTD("新台幣");

    private String chinese;

    Currency(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return this.chinese;
    }
}
