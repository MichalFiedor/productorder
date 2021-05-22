package com.fiedormichal.productOrder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiedormichal.productOrder.model.Order;
import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.service.OrderService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void givenTimePeriod_whenGetOrdersFromPeriod_thenReturnStatusOk() throws Exception {
        //given
        JSONObject timePeriod = new JSONObject();
        timePeriod.put("beginningOfPeriod", "2021-05-19");
        timePeriod.put("endOfPeriod", "2021-05-22");

        mockMvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(timePeriod.toJSONString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenBlankTimePeriod_whenGetOrdersFromPeriod_thenReturnBadRequestStatus() throws Exception {
        //given
        JSONObject timePeriod = new JSONObject();
        timePeriod.put("beginningOfPeriod", "");
        timePeriod.put("endOfPeriod", "2021-05-22");

        mockMvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(timePeriod.toJSONString()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenOrder_whenAddOrder_thenReturnStatusOk() throws Exception {

        Order order = new Order();
        Product product1 = new Product();
        product1.setId(1);
        Product product2 = new Product();
        product2.setId(2);
        order.setProducts(Arrays.asList(product1, product2));
        String orderAsString = objectMapper.writeValueAsString(order);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderAsString))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenOrderWithoutProducts_whenAddOrder_thenReturnBadRequestStatus() throws Exception {

        Order order = new Order();
        String orderAsString = objectMapper.writeValueAsString(order);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderAsString))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenOrderId_recalculateOrder_thenReturnStatusOk() throws Exception {

        mockMvc.perform(put("/orders/1/recalculate")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenIncorrectOrderId_recalculateOrder_thenReturnBadRequestStatus() throws Exception {

        mockMvc.perform(put("/orders/1d/recalculate")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}