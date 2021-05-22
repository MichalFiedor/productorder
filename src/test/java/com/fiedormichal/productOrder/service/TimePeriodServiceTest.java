package com.fiedormichal.productOrder.service;

import com.fiedormichal.productOrder.exception.IncorrectDateException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TimePeriodServiceTest {

    @Autowired
    private TimePeriodService timePeriodService;

    @Test
    void givenDateAsString_whenPrepareBeginningOfPeriod_thenReturnCorrectLocalDateTime(){
        //given
        String date = "2021-05-19";
        LocalDateTime expected = LocalDateTime.of(2021,05,19, 00,00,00);
        //when
        LocalDateTime result = timePeriodService.prepareBeginningOfPeriod(date);
        //then
        assertEquals(expected, result);
    }

    @Test
    void givenDateAsString_whenPrepareEndOfPeriod_thenReturnCorrectLocalDateTime(){
        //given
        String date = "2021-05-20";
        LocalDateTime expected = LocalDateTime.of(2021,05,20, 23,59,59);
        //when
        LocalDateTime result = timePeriodService.prepareEndOfPeriod(date);
        //then
        assertEquals(expected, result);
    }

    @Test
    void whenBeginningOfPeriodIsAfterEndOfPeriod_shouldThrowIncorrectDateException(){
        LocalDateTime beginning = LocalDateTime.of(2021,05,20, 23,59,59);
        LocalDateTime end = LocalDateTime.of(2021,05,19, 00,00,00);
        assertThrows(IncorrectDateException.class, ()->timePeriodService.endIsAfterBeginning(beginning, end));
    }

}