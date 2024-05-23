package com.example.diplomproject.config.annotation.ivalidator.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.validation.ConstraintValidatorContext;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class DateOfBirthRangeValidatorTest {
    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    DateOfBirthRangeValidator validator;

    @Test
    public void testValidDateInRange() {
        Date validDate = Date.valueOf("1990-06-15");
        boolean isValid = validator.isValid(validDate, constraintValidatorContext);
        assertTrue(isValid);
    }
    @Test
    public void testInvalidDateBeforeMinDate() {
        Date invalidDate = Date.valueOf("1900-12-31");
        boolean isValid = validator.isValid(invalidDate, constraintValidatorContext);
        assertFalse(isValid);
    }
    @Test
    public void testInvalidDateAfterMaxDate() {
        Date invalidDate = Date.valueOf("2020-02-29");
        boolean isValid = validator.isValid(invalidDate, constraintValidatorContext);
        assertFalse(isValid);
    }
    @Test
    public void testInvalidNullDate() {
        Date invalidDate = null;
        boolean isValid = validator.isValid(invalidDate, constraintValidatorContext);
        assertFalse(isValid);
    }
    @Test
    public void testValidMinDate() {
        Date validDate = Date.valueOf("1920-01-01");
        boolean isValid = validator.isValid(validDate, constraintValidatorContext);
        assertTrue(isValid);
    }
}