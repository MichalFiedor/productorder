package com.fiedormichal.productOrder.controller;

import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Object>getProducts(){
        return ResponseEntity.ok().body(productService.getProducts());
    }

    @PostMapping("/products")
    public ResponseEntity<Object> addProduct(@Valid @RequestBody Product product){
        return ResponseEntity.ok().body(productService.addProduct(product));
    }

    @PutMapping("/products")
    public ResponseEntity<Object> updateProduct(@Valid @RequestBody Product product){
        return ResponseEntity.ok().body(productService.updateProduct(product));
    }
}
