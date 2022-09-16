package com.systex.practice.controller.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckDateValidator implements ConstraintValidator<CheckDate, Object> {

    private String dateFormat;

    @Override
    public void initialize(CheckDate constraintAnnotation) {
        this.dateFormat = constraintAnnotation.dateFormat();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value instanceof String) {
            String stringValue = (String) value;
            System.out.println(stringValue);
            SimpleDateFormat dateFormat = new SimpleDateFormat(this.dateFormat);
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(stringValue);
            }
            catch (ParseException e) {
                return false;
            }
            return true;
        }

        return false;

    }

}
