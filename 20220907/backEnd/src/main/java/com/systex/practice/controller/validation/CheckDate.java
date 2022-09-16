package com.systex.practice.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {CheckDateValidator.class})
public @interface CheckDate {

    String message() default "dateFormat is not correct";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};

    String dateFormat();
}
