package com.example.diplomproject.config.annotation.ivalidator.validator;

import com.example.diplomproject.config.annotation.imp.ValidDateFetcher;
import com.example.diplomproject.config.annotation.imp.ValidDateOfBirthRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;
import java.time.LocalDate;

public class DateOfFetcher implements ConstraintValidator<ValidDateFetcher, Date> {
    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        LocalDate localDate = date.toLocalDate();

        Date maxDate = Date.valueOf("2027-01-01");
        if (localDate.isAfter(maxDate.toLocalDate())) {
            return false;
        }
        return true;
    }
}
