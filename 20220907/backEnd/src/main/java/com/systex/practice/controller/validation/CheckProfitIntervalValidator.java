package com.systex.practice.controller.validation;

import com.systex.practice.controller.dto.request.FindStockRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class CheckProfitIntervalValidator implements
        ConstraintValidator<CheckProfitInterval, FindStockRequest.ProfitInterval> {

    @Override
    public void initialize(CheckProfitInterval constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(FindStockRequest.ProfitInterval profitInterval, ConstraintValidatorContext constraintValidatorContext) {

        if (profitInterval == null) return true;

        BigDecimal min = profitInterval.getProfitMin();
        BigDecimal max = profitInterval.getProfitMax();

        if (min != null && max != null) {
            if (min.compareTo(max) == 1)
                return false;
            else
                return true;
        }

        return true;
    }
}
