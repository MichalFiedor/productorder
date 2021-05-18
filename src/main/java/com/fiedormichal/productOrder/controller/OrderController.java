package com.fiedormichal.productOrder.controller;

import com.fiedormichal.productOrder.model.Order;
import com.fiedormichal.productOrder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Object> addOrder(@Valid @RequestBody Order order){
        return ResponseEntity.ok().body(orderService.addOrder(order));
    }

}
