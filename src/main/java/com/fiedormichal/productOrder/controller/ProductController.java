package com.fiedormichal.productOrder.controller;

import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.service.ProductService;
import com.fiedormichal.productOrder.validator.schemaValidator.ValidJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.fiedormichal.productOrder.validator.schemaValidator.SchemaPath.POST_PRODUCT_SCHEMA;
import static com.fiedormichal.productOrder.validator.schemaValidator.SchemaPath.PUT_PRODUCT_SCHEMA;


@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Object>getProducts(){
        return ResponseEntity.ok().body(productService.getProducts());
    }

    @PostMapping(value = "/products")
    public ResponseEntity<Object> addProduct(@ValidJson(POST_PRODUCT_SCHEMA) Product product) {

        return ResponseEntity.ok().body(productService.addProduct(product));
    }

    @PutMapping(value = "/products")
    public ResponseEntity<Object> updateProduct(@ValidJson(PUT_PRODUCT_SCHEMA) Product product){
        return ResponseEntity.ok().body(productService.updateProduct(product));
    }
}
