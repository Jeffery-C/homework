package com.systex.practice.controller.exception;

public class StockWebHttpException extends Exception{

    private StatusCode statusCode;

    StockWebHttpException() {}

    public StockWebHttpException(StatusCode statusCode, String logMessage) {
        super(logMessage);
        this.statusCode = statusCode;
    }

    public StockWebHttpException(StatusCode statusCode) {
        super();
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return this.statusCode;
    }
}
