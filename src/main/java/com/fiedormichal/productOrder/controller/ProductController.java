package com.fiedormichal.productOrder.controller;

import com.fiedormichal.productOrder.Dto.ProductDtoMapper;
import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Object>getProducts(){
        return ResponseEntity.ok().body(ProductDtoMapper.mapToDtos(productService.getProducts()));
    }

    @PostMapping("/products")
    public ResponseEntity<Object> addProduct(@Valid @RequestBody Product product){
        return ResponseEntity.ok().body(ProductDtoMapper.mapToDto(productService.addProduct(product)));
    }
}
