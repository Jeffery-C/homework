package com.systex.practice.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {CheckProfitIntervalValidator.class})
public @interface CheckProfitInterval {

    String message() default "the format of profit interval is not correct";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};

}
