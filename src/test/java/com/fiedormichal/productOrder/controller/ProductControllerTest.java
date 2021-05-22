package com.fiedormichal.productOrder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.service.ProductService;
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

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void shouldReturnProductList() throws Exception {
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Cap");

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("shoes");

        Product product3 = new Product();
        product3.setId(2);
        product3.setName("jacket");

        when(productService.getProducts()).thenReturn(Arrays.asList(product1, product2, product3));

        mockMvc.perform(get("/products")
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(product1.getId())))
                .andExpect(jsonPath("$[1].name", is(product2.getName())))
                .andExpect(jsonPath("$[2].name", is(product3.getName())));
    }

    @Test
    void givenPostAsJson_whenAddProduct_ThenReturnStatusOk() throws Exception {
        Product product = new Product();
        product.setName("Cap");
        product.setPrice(BigDecimal.valueOf(200));

        String productAsString = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productAsString))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenIncorrectPrice_whenAddProduct_ThenReturnBadRequestStatus() throws Exception {
        Product product = new Product();
        product.setName("Cap");
        product.setPrice(BigDecimal.valueOf(0.1));

        String productAsString = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productAsString))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenIncorrectName_whenAddProduct_ThenReturnBadRequestStatus() throws Exception {
        Product product = new Product();
        product.setName("C");
        product.setPrice(BigDecimal.valueOf(100));

        String productAsString = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productAsString))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenUpdatedProduct_whenUpdateProduct_ThenReturnStatusOk() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("Cap");
        product.setPrice(BigDecimal.valueOf(250));

        String productAsString = objectMapper.writeValueAsString(product);

        mockMvc.perform(put("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productAsString))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenIncorrectPriceInUpdatedProduct_whenUpdateProduct_ThenReturnBadRequestStatus() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("Cap");
        product.setPrice(BigDecimal.valueOf(0.1));

        String productAsString = objectMapper.writeValueAsString(product);

        mockMvc.perform(put("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productAsString))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenIncorrectNameInUpdatedProduct_whenUpdateProduct_ThenReturnBadRequestStatus() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("C");
        product.setPrice(BigDecimal.valueOf(100));

        String productAsString = objectMapper.writeValueAsString(product);

        mockMvc.perform(put("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productAsString))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenUpdatedProductWithoutId_whenUpdateProduct_ThenReturnBadRequestStatus() throws Exception {
        JSONObject product = new JSONObject();
        product.put("name", "cap");
        product.put("price", 250);

        mockMvc.perform(put("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(product.toJSONString()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}