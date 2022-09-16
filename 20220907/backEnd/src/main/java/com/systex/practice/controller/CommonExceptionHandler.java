package com.systex.practice.controller;


import com.systex.practice.controller.dto.response.StatusResponse;
import com.systex.practice.controller.exception.StatusCode;
import com.systex.practice.controller.exception.StockWebHttpException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public StatusResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new StatusResponse(new StockWebHttpException(
                StatusCode.BAD_REQUEST, "throws HttpMessageNotReadableException"));
    }

    @ExceptionHandler({SQLException.class})
    @ResponseBody
    public StatusResponse handleSQLException(SQLException e) {
        return new StatusResponse(new StockWebHttpException(
                StatusCode.INTERNAL_SERVER_ERROR, "throws SQLException"));
    }

//    @ExceptionHandler({Exception.class})
//    @ResponseBody
//    public StatusResponse handleException(Exception e) {
//        return new StatusResponse(new StockWebHttpException(
//                StatusCode.BAD_REQUEST, "");
//    }

}
