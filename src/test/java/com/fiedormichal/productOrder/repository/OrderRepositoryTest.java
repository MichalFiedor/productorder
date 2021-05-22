package com.fiedormichal.productOrder.repository;

import com.fiedormichal.productOrder.model.Order;
import com.fiedormichal.productOrder.model.Product;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void givenTimePeriod_whenFindAllOrdersForGivenPeriod_thenReturnListOfOrders(){
        //given
        Product product = new Product();
        product.setName("cap");
        product.setPrice(BigDecimal.valueOf(125));
        testEntityManager.persist(product);
        Order order1 = new Order();
        order1.setOrderedAt(LocalDateTime.of(2021, 05,15,12,25));
        order1.setProducts(Arrays.asList(product));
        testEntityManager.persist(order1);
        Order order2 = new Order();
        order2.setOrderedAt(LocalDateTime.of(2021, 05,17,12,25));
        order2.setProducts(Arrays.asList(product));
        testEntityManager.persist(order2);
        Order order3 = new Order();
        order3.setOrderedAt(LocalDateTime.of(2021, 05,20,12,25));
        order3.setProducts(Arrays.asList(product));
        testEntityManager.persist(order3);
        LocalDateTime start = LocalDateTime.of(2021, 05, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2021, 05, 18, 0, 0);
        //when
        List<Order> result = orderRepository.findAllOrdersForGivenPeriod(start, end);
        //then
        assertEquals(2, result.size());
        assertEquals(2, result.get(1).getId());
    }
}