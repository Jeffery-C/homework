package com.systex.practice.controller;

import com.systex.practice.controller.dto.request.DeliveryAmountRequest;
import com.systex.practice.controller.dto.request.FindStockRequest;
import com.systex.practice.controller.dto.request.InsertStockRequest;
import com.systex.practice.controller.dto.request.UpdateStockPriceRequest;
import com.systex.practice.controller.dto.response.Result;
import com.systex.practice.controller.dto.response.StatusResponse;
import com.systex.practice.controller.exception.StatusCode;
import com.systex.practice.controller.exception.StockWebHttpException;
import com.systex.practice.controller.validation.ValidList;
import com.systex.practice.model.entity.HolidaySchedule;
import com.systex.practice.model.entity.type.HolidayType;
import com.systex.practice.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class StockController {

    @Autowired
    private StockService stockService;

    public void validRequestBody(BindingResult bindingResult) throws StockWebHttpException {
        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                message.append(objectError.getDefaultMessage()).append(", ");
            }
            throw new StockWebHttpException(StatusCode.BAD_REQUEST, message.toString());
        }
    }


    @PostMapping(value = "/unreal/detail")
    public StatusResponse getStockDetail(@RequestBody @Valid FindStockRequest findStockRequest,
                                         BindingResult bindingResult) {

        List<Result> resultList;

        try {
            this.validRequestBody(bindingResult);
            resultList = this.stockService.getStockDetail(findStockRequest);
        } catch (StockWebHttpException ex) {
            return new StatusResponse(ex);
        }

        return new StatusResponse(new StockWebHttpException(StatusCode.OK, "OK"), resultList);
    }

    @PostMapping(value = "/unreal/sum")
    public StatusResponse getStockSummary(@RequestBody @Valid FindStockRequest findStockRequest,
                                          BindingResult bindingResult) {
        List<Result> resultList;

        try {
            this.validRequestBody(bindingResult);
            resultList = this.stockService.getStockSummary(findStockRequest);
        } catch (StockWebHttpException ex) {
            return new StatusResponse(ex);
        }

        return new StatusResponse(new StockWebHttpException(StatusCode.OK, "OK"), resultList);
    }

    @PostMapping(value = "/unreal/add")
    public StatusResponse insertStock(@RequestBody @Valid InsertStockRequest insertStockRequest,
                                      BindingResult bindingResult) {
        List<Result> resultList;

        try {
            this.validRequestBody(bindingResult);
            resultList = this.stockService.insertStock(insertStockRequest);
        } catch (StockWebHttpException ex) {
            return new StatusResponse(ex);
        }

        return new StatusResponse(new StockWebHttpException(StatusCode.OK, "OK"), resultList);

    }


    @PutMapping(value = "/stock")
    public StatusResponse updateStockPrice(
            @RequestBody @Valid ValidList<UpdateStockPriceRequest> updateStockPriceRequestList,
            BindingResult bindingResult) {

        List<Result> resultList;

        try {
            this.validRequestBody(bindingResult);
            this.stockService.updateStockPrice(updateStockPriceRequestList);
        } catch (StockWebHttpException ex) {
            return new StatusResponse(ex);
        }

        return new StatusResponse(new StockWebHttpException(
                StatusCode.OK, "OK for update stock price list"));

    }

    @PostMapping("/stock")
    public StatusResponse getDeliveryAmount(
            @RequestBody @Valid DeliveryAmountRequest deliveryAmountRequest,
            BindingResult bindingResult) {

        List<Result> resultList;

        try {
            this.validRequestBody(bindingResult);
            resultList = this.stockService.getDeliveryAmount(deliveryAmountRequest);
        } catch (StockWebHttpException ex) {
            return new StatusResponse(ex);
        }

        return new StatusResponse(new StockWebHttpException(StatusCode.OK, "OK"), resultList);
    }


}















