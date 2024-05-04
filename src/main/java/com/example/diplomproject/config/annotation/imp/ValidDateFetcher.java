package com.example.diplomproject.config.annotation.imp;

import com.example.diplomproject.config.annotation.ivalidator.validator.DateOfBirthRangeValidator;
import com.example.diplomproject.config.annotation.ivalidator.validator.DateOfFetcher;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateOfFetcher.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateFetcher {
    String message() default "Invalid date of fetcher";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
