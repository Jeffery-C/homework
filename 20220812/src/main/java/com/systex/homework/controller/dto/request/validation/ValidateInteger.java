package com.systex.homework.controller.dto.request.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IntegerValidator.class})
public @interface ValidateInteger {

    String message() default "parameter value must be a integer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
