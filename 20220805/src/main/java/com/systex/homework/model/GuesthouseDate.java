package com.systex.homework.model;

import lombok.Getter;

@Getter
public class GuesthouseDate {
    private int guesthouseId;
    private String dateString;

    public GuesthouseDate() {
    }

    public GuesthouseDate(int guesthouseId, String dateString) {
        this.guesthouseId = guesthouseId;
        this.dateString = dateString;
    }
}
