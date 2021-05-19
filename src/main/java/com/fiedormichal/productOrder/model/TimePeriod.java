package com.fiedormichal.productOrder.model;

import com.fiedormichal.productOrder.validator.EndOfPeriodIsAfterBeginningOfPeriod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EndOfPeriodIsAfterBeginningOfPeriod(message = "Beginning of period must be before end of period")
public class TimePeriod {
    private String beginningOfPeriod;
    private String endOfPeriod;


}
