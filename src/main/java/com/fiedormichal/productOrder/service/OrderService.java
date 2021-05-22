package com.fiedormichal.productOrder.service;

import com.fiedormichal.productOrder.exception.IncorrectDateException;
import com.fiedormichal.productOrder.exception.OrderNotFoundException;
import com.fiedormichal.productOrder.model.Order;
import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.model.TimePeriod;
import com.fiedormichal.productOrder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final TimePeriodService timePeriodService;

    @Transactional
    public Order addOrder(Order order){
        List<Product> productsFromOrder = productService.getAllProductsFromOrder(order);
        order.addProducts(productsFromOrder);
        order.setTotalCost(getTotalCost(order));

        return orderRepository.save(order);
    }

    @Transactional
    public Order recalculateOrder(int orderId) {
        Order orderFromDB = findById(orderId);
        orderFromDB.setTotalCost(getTotalCost(orderFromDB));
        return orderFromDB;
    }

    public List<Order> getOrdersFromPeriod(TimePeriod period) {
        LocalDateTime beginning;
        LocalDateTime end;
        try{
            beginning = timePeriodService.prepareBeginningOfPeriod(period.getBeginningOfPeriod());
            end = timePeriodService.prepareEndOfPeriod(period.getEndOfPeriod());
        }catch (Exception exception){
            throw new IncorrectDateException("Invalid date format. Correct format: yyyy-MM-dd");
        }
        timePeriodService.endIsAfterBeginning(beginning, end);
        return orderRepository.findAllOrdersForGivenPeriod(beginning, end);
    }

    public Order findById(int id){
        return orderRepository.findById(id).orElseThrow(
                ()-> new OrderNotFoundException("Order with id:" + id + " does not exist."));
    }

    private BigDecimal getTotalCost(Order order){
        BigDecimal totalCost = BigDecimal.ZERO;
        List<Integer> productsID = order.getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList());
        for(Integer productId: productsID){
            totalCost = totalCost.add(productService.getProductPrice(productId));
        }
    return totalCost;
    }
}
