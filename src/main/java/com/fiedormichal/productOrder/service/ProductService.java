package com.fiedormichal.productOrder.service;

import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product>getProducts(){
        return productRepository.findAll();
    }
}
