package com.example.diplomproject.config.annotation.ivalidator.validator;

import com.example.diplomproject.config.annotation.imp.ValidDate;
import com.example.diplomproject.config.annotation.imp.ValidDateOfBirthRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;
import java.time.LocalDate;

public class DateOfNow implements ConstraintValidator<ValidDate, Date> {

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        LocalDate localDate = date.toLocalDate();
        Date minDate = Date.valueOf(LocalDate.now());
        if (localDate.isBefore(minDate.toLocalDate())) {
            return false;
        }
        return true;
    }
}
