package com.fiedormichal.productOrder.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EndOfPeriodValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndOfPeriodIsAfterBeginningOfPeriod {
    String message() default "Invalid end of period";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
