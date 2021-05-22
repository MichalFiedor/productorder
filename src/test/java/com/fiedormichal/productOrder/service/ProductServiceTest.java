package com.fiedormichal.productOrder.service;

import com.fiedormichal.productOrder.exception.ProductNotFoundException;
import com.fiedormichal.productOrder.model.Order;
import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.repository.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;


    @Test
    void givenProductWithChangedNameAndPrice_whenUpdateProduct_thenReturnUpdatedProduct(){
        //given
        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName("Cap");
        updatedProduct.setPrice(BigDecimal.valueOf(205));

        Product productFromDB = new Product();
        productFromDB.setId(1);
        productFromDB.setName("cap");
        productFromDB.setPrice(BigDecimal.valueOf(190));
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(productFromDB));
        //when
        Product result = productService.updateProduct(updatedProduct);
        //then
        assertEquals(updatedProduct.getId(), result.getId());
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getPrice(), result.getPrice());
    }

    @Test
    void givenProductWithChangedName_whenUpdateProduct_thenReturnUpdatedProduct(){
        //given
        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName("Shoes");

        Product productFromDB = new Product();
        productFromDB.setId(1);
        productFromDB.setName("cap");
        productFromDB.setPrice(BigDecimal.valueOf(190));
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(productFromDB));
        //when
        Product result = productService.updateProduct(updatedProduct);
        //then
        assertEquals(updatedProduct.getId(), result.getId());
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(productFromDB.getPrice(), result.getPrice());
    }

    @Test
    void givenProductWithChangedPrice_whenUpdateProduct_thenReturnUpdatedProduct(){
        //given
        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setPrice(BigDecimal.valueOf(432.99));

        Product productFromDB = new Product();
        productFromDB.setId(1);
        productFromDB.setName("cap");
        productFromDB.setPrice(BigDecimal.valueOf(190));
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(productFromDB));
        //when
        Product result = productService.updateProduct(updatedProduct);
        //then
        assertEquals(updatedProduct.getId(), result.getId());
        assertEquals(productFromDB.getName(), result.getName());
        assertEquals(productFromDB.getPrice(), result.getPrice());
    }

    @Test
    void givenIncorrectId_whenUpdateProduct_thenThrowProductNotFoundException(){
        when(productRepository.findById(1)).thenReturn(Optional.empty());
        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        assertThrows(ProductNotFoundException.class, ()-> productService.updateProduct(updatedProduct));
    }

    @Test
    void givenOrderWithProductId_whenGetAllProductsFromOrder_thenReturnProductsFromDB(){
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
        productFromDB1.setPrice(BigDecimal.valueOf(150));

        Product productFromDB2 = new Product();
        productFromDB2.setId(2);
        productFromDB2.setName("shoes");
        productFromDB2.setPrice(BigDecimal.valueOf(425.50));
        when(productRepository.findById(1)).thenReturn(Optional.of(productFromDB1));
        when(productRepository.findById(2)).thenReturn(Optional.of(productFromDB2));
        //when
        List<Product> result = productService.getAllProductsFromOrder(order);
        //then
        assertEquals(2, result.size());
        assertEquals(productFromDB1.getPrice(), result.get(0).getPrice());
        assertEquals(productFromDB2.getName(), result.get(1).getName());
    }

}