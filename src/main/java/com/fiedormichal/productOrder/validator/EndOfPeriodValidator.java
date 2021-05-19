package com.fiedormichal.productOrder.validator;

import com.fiedormichal.productOrder.model.TimePeriod;
import com.fiedormichal.productOrder.parser.DateParser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class EndOfPeriodValidator implements ConstraintValidator<EndOfPeriodIsAfterBeginningOfPeriod, TimePeriod> {

    @Override
    public void initialize(EndOfPeriodIsAfterBeginningOfPeriod constraintAnnotation) {

    }

    @Override
    public boolean isValid(TimePeriod timePeriod, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate beginningOfPeriod = DateParser.parseToLocalDate(timePeriod.getBeginningOfPeriod());
        LocalDate endOfPeriod = DateParser.parseToLocalDate(timePeriod.getEndOfPeriod());
        if(endOfPeriod.isAfter(beginningOfPeriod)){
            return true;
        }
        return false;
    }
}
