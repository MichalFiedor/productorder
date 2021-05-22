package com.fiedormichal.productOrder.model;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderTest {

    @Test
    void givenProducts_whenAddProducts_thenPutItInProductListInClassObject(){
        //given
        Order order = new Order();
        Product productFromUser1 = new Product();
        productFromUser1.setId(1);
        Product productFromUser2 = new Product();
        productFromUser2.setId(2);
        order.setProducts(Arrays.asList(productFromUser1, productFromUser2));

        Product productFromDB1 = new Product();
        productFromDB1.setId(1);
        productFromDB1.setName("Cap");
        productFromDB1.setPrice(BigDecimal.valueOf(150));

        Product productFromDB2 = new Product();
        productFromDB2.setId(2);
        productFromDB2.setName("shoes");
        //when
        order.addProducts(Arrays.asList(productFromDB1, productFromDB2));
        //then
        assertEquals(productFromDB1.getId(), order.getProducts().get(0).getId());
        assertEquals(productFromDB1.getName(), order.getProducts().get(0).getName());
        assertEquals(productFromDB2.getPrice(), order.getProducts().get(1).getPrice());
        assertEquals(productFromDB2.getId(), order.getProducts().get(1).getId());
    }
}