package com.fiedormichal.productOrder.service;

import com.fiedormichal.productOrder.exception.ProductNotFoundException;
import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    public Product findProduct(int id){
       return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id:" + id + " does not exist."));
    }

    @Transactional
    public Product updateProduct(Product product) {
        Product productFromDB = findProduct(product.getId());
        productFromDB.setName(product.getName());
        productFromDB.setPrice(product.getPrice());
        return productFromDB;
    }

    public BigDecimal getProductPrice(Integer productId) {
        return findProduct(productId).getPrice();
    }
}
