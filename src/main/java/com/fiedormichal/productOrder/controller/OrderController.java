package com.fiedormichal.productOrder.controller;

import com.fiedormichal.productOrder.model.Order;
import com.fiedormichal.productOrder.model.TimePeriod;
import com.fiedormichal.productOrder.service.OrderService;
import com.fiedormichal.productOrder.validator.schemaValidator.ValidJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.fiedormichal.productOrder.validator.schemaValidator.SchemaPath.POST_ORDER_SCHEMA;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<Object> getOrdersFromPeriod(@Valid @RequestBody TimePeriod period){
        return ResponseEntity.ok().body(orderService.getOrdersFromPeriod(period));
    }

    @PostMapping("/orders")
    public ResponseEntity<Object> addOrder(@ValidJson(POST_ORDER_SCHEMA) Order order){
        return ResponseEntity.ok().body(orderService.addOrder(order));
    }

    @PutMapping("/orders/{orderId}/recalculate")
    public ResponseEntity<Object> recalculateOrder(@PathVariable int orderId){
        return ResponseEntity.ok().body(orderService.recalculateOrder(orderId));
    }

}
