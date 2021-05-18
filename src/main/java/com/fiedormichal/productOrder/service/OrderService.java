package com.fiedormichal.productOrder.service;

import com.fiedormichal.productOrder.exception.ProductNotFoundException;
import com.fiedormichal.productOrder.model.Order;
import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.repository.OrderRepository;
import com.fiedormichal.productOrder.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Order addOrder(Order order){
        order.setTotalCost(getTotalCost(order));
        return orderRepository.save(order);
    }

    private BigDecimal getTotalCost(Order order){
        BigDecimal totalCost = BigDecimal.ZERO;
        List<Integer> productsID = order.getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList());
        for(Integer productId: productsID){
            totalCost = totalCost.add(getProductPrice(productId));
        }
    return totalCost;
    }

    private BigDecimal getProductPrice(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product with id:" + productId + " does not exist."));
        return product.getPrice();
    }

}
