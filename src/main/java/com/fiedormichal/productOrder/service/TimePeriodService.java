package com.fiedormichal.productOrder.service;

import com.fiedormichal.productOrder.exception.IncorrectDateException;
import com.fiedormichal.productOrder.parser.DateParser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimePeriodService {

    public LocalDateTime prepareBeginningOfPeriod(String beginningOfPeriod){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(beginningOfPeriod);
        stringBuilder.append("-00-00-00");
        return DateParser.parseToLocalDateTime(stringBuilder.toString());
    }

    public LocalDateTime prepareEndOfPeriod(String endOfPeriod){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(endOfPeriod);
        stringBuilder.append("-23-59-59");
        return DateParser.parseToLocalDateTime(stringBuilder.toString());
    }

    public void endIsAfterBeginning(LocalDateTime beginning, LocalDateTime end){
        if(beginning.isAfter(end)){
            throw new IncorrectDateException("End of period must be before beginning of period");
        }
    }
}
