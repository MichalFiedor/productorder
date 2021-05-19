package com.fiedormichal.productOrder.controller;

import com.fiedormichal.productOrder.model.Order;
import com.fiedormichal.productOrder.model.TimePeriod;
import com.fiedormichal.productOrder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @GetMapping("/orders")
    public ResponseEntity<Object> getOrdersFromPeriod(@RequestBody TimePeriod period){

        return ResponseEntity.ok().body(orderService.getOrdersFromPeriod(period));
    }

    @PostMapping("/orders")
    public ResponseEntity<Object> addOrder(@Valid @RequestBody Order order){
        return ResponseEntity.ok().body(orderService.addOrder(order));
    }

    @PutMapping("/orders/{orderId}/recalculate")
    public ResponseEntity<Object> recalculateOrder(@PathVariable int orderId){
        return ResponseEntity.ok().body(orderService.recalculateOrder(orderId));
    }

}
