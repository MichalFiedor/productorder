package com.fiedormichal.productOrder.service;

import com.fiedormichal.productOrder.model.Order;
import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.model.TimePeriod;
import com.fiedormichal.productOrder.repository.OrderRepository;
import com.fiedormichal.productOrder.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private TimePeriodService timePeriodService;

    @Test
    void shouldReturnOrderWithCalculatedTotalCost() {
        //given
        Product productFromOrder1 = new Product();
        productFromOrder1.setId(1);
        Product productFromOrder2 = new Product();
        productFromOrder2.setId(2);

        Order order = new Order();
        order.setProducts(Arrays.asList(productFromOrder1, productFromOrder2));

        Product productFromDB1 = new Product();
        productFromDB1.setId(1);
        productFromDB1.setName("Cap");
        productFromDB1.setPrice(BigDecimal.valueOf(150.99));

        Product productFromDB2 = new Product();
        productFromDB2.setId(2);
        productFromDB2.setName("shoes");
        productFromDB2.setPrice(BigDecimal.valueOf(425.50));

        when(productService.getAllProductsFromOrder(order)).thenReturn(Arrays.asList(productFromDB1, productFromDB2));
        when(productService.getProductPrice(1)).thenReturn(BigDecimal.valueOf(150.99));
        when(productService.getProductPrice(2)).thenReturn(BigDecimal.valueOf(425.50));
        when(orderRepository.save(order)).thenReturn(order);
        //when
        Order result = orderService.addOrder(order);
        //then
        assertEquals(BigDecimal.valueOf(150.99 + 425.50), result.getTotalCost());
    }

    @Test
    void givenTimePeriod_whenGetOrdersFromPeriod_thenReturnOrdersFromPeriod() {
        //given
        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setBeginningOfPeriod("2021-05-19");
        timePeriod.setEndOfPeriod("2021-05-23");
        Order order1 = new Order();
        order1.setId(1);
        Order order2 = new Order();
        order2.setId(2);
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        LocalDateTime beginning = timePeriodService.prepareBeginningOfPeriod(timePeriod.getBeginningOfPeriod());
        LocalDateTime end = timePeriodService.prepareEndOfPeriod(timePeriod.getEndOfPeriod());

        when(orderRepository.findAllOrdersForGivenPeriod(beginning, end)).thenReturn(orders);
        //when
        List<Order> result = orderService.getOrdersFromPeriod(timePeriod);
        //then
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }


    @Test
    void shouldRecalculateOrderCost() {
        //given
        Order notRecalculatedOrder = new Order();
        Product productWithUpdatedPrice1 = new Product();
        productWithUpdatedPrice1.setId(1);
        productWithUpdatedPrice1.setName("cap");
        productWithUpdatedPrice1.setPrice(BigDecimal.valueOf(249.99));

        Product productWithUpdatedPrice2 = new Product();
        productWithUpdatedPrice2.setId(2);
        productWithUpdatedPrice2.setName("shoes");
        productWithUpdatedPrice2.setPrice(BigDecimal.valueOf(335.99));
        notRecalculatedOrder.addProducts(Arrays.asList(productWithUpdatedPrice1, productWithUpdatedPrice2));
        notRecalculatedOrder.setTotalCost(BigDecimal.valueOf(500));
        when(orderRepository.findById(1)).thenReturn(Optional.of(notRecalculatedOrder));
        when(productService.getProductPrice(1)).thenReturn(BigDecimal.valueOf(249.99));
        when(productService.getProductPrice(2)).thenReturn(BigDecimal.valueOf(335.99));
        //when
        Order result = orderService.recalculateOrder(1);
        //then
        assertEquals(BigDecimal.valueOf(249.99 + 335.99), result.getTotalCost());
    }
}