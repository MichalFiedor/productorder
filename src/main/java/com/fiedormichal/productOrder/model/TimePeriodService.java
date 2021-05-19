package com.fiedormichal.productOrder.model;

import com.fiedormichal.productOrder.parser.DateParser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimePeriodService {

    public LocalDateTime prepareBeginningOfPeriod(String beginningOfPeriod){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(beginningOfPeriod);
        stringBuilder.append("-00-00-00");
        return DateParser.parse(stringBuilder.toString());
    }

    public LocalDateTime prepareEndOfPeriod(String endOfPeriod){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(endOfPeriod);
        stringBuilder.append("-23-59-59");
        return DateParser.parse(stringBuilder.toString());
    }
}
