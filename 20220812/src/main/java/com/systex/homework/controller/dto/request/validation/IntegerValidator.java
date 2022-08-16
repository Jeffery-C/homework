package com.systex.homework.controller.dto.request.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntegerValidator implements ConstraintValidator<ValidateInteger, Object> {

    @Override
    public void initialize(ValidateInteger constraintAnnotation) {
        System.out.println(constraintAnnotation.message());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof String) return false;
        if (value instanceof Double) {
            double doubleValue = (double) value;
            if ((doubleValue - (int)doubleValue) != 0) return false;
        }
        return true;
    }
}
