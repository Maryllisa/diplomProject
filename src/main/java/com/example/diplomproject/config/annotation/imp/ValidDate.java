package com.example.diplomproject.config.annotation.imp;

import com.example.diplomproject.config.annotation.ivalidator.validator.DateOfBirthRangeValidator;
import com.example.diplomproject.config.annotation.ivalidator.validator.DateOfNow;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateOfNow.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "Invalid date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
