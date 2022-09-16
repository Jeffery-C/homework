package com.systex.practice.controller.exception;

public enum StatusCode {

    OK("000", ""),
    NO_FOUND("001", "查無符合資料"),
    BAD_REQUEST("002", "參數檢核錯誤"),
    INTERNAL_SERVER_ERROR("005", "伺服器忙碌中，請稍後嘗試");

    private String code;
    private String message;

    StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
