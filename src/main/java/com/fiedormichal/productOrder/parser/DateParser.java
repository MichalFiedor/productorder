package com.fiedormichal.productOrder.parser;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateParser {

    public static LocalDateTime parseToLocalDateTime(String date){
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
    }
}
