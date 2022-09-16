package com.systex.practice.controller.dto.response;

import com.systex.practice.controller.exception.StockWebHttpException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@Getter
@Setter
@NoArgsConstructor
public class StatusResponse {

    private List<Result> resultList = new ArrayList<>();

    private String responseCode;

    private String message;

    public StatusResponse(StockWebHttpException stockWebHttpException, List<Result> resultList) {
        this.resultList = resultList;
        this.responseCode = stockWebHttpException.getStatusCode().getCode();
        this.message = stockWebHttpException.getStatusCode().getMessage();
        this.display(stockWebHttpException);
    }

    public StatusResponse(StockWebHttpException stockWebHttpException) {
        this.responseCode = stockWebHttpException.getStatusCode().getCode();
        this.message = stockWebHttpException.getStatusCode().getMessage();
        this.display(stockWebHttpException);
    }

    public void display(StockWebHttpException stockWebHttpException) {
        System.out.println(now() + ", " + this.responseCode + ", " + this.message + ", " +
                stockWebHttpException.getMessage());
    }
}
