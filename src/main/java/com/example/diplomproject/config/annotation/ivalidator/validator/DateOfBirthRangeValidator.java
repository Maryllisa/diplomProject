package com.example.diplomproject.config.annotation.ivalidator.validator;

import com.example.diplomproject.config.annotation.imp.ValidDateOfBirthRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;
import java.time.LocalDate;

import static org.bouncycastle.asn1.isismtt.x509.DeclarationOfMajority.dateOfBirth;

public class DateOfBirthRangeValidator implements ConstraintValidator<ValidDateOfBirthRange, Date> {

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        LocalDate localDate = date.toLocalDate();

        Date minDate = Date.valueOf("1920-01-01");
        Date maxDate = Date.valueOf("2016-01-01");
        if (localDate.isBefore(minDate.toLocalDate()) || localDate.isAfter(maxDate.toLocalDate())) {
            return false;
        }
        return true;
    }
}