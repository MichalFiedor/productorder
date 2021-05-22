package com.fiedormichal.productOrder.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TimePeriod {
    @NotBlank(message = "Beginning of period can not be blank")
    private String beginningOfPeriod;
    @NotBlank(message = "End of period can not be blank")
    private String endOfPeriod;
}
