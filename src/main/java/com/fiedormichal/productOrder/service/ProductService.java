package com.fiedormichal.productOrder.service;

import com.fiedormichal.productOrder.exception.ProductNotFoundException;
import com.fiedormichal.productOrder.model.Order;
import com.fiedormichal.productOrder.model.Product;
import com.fiedormichal.productOrder.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {
    private final ProductRepository productRepository;

    public Product addProduct(Product product){
        Product addedProduct = productRepository.save(product);
        log.info("Product with id: " + addedProduct.getId() + ", name: " + addedProduct.getName() + " and price: "
                + addedProduct.getPrice() + " has been saved");
        return addedProduct;
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
        if(product.getName()!=null){
            productFromDB.setName(product.getName());
        }
        if(product.getPrice()!=null){
            productFromDB.setPrice(product.getPrice());
        }
        log.info("Product with id: " + productFromDB.getId() + " has been updated");
        return productFromDB;
    }

    public BigDecimal getProductPrice(Integer productId) {
        return findProduct(productId).getPrice();
    }

    public List<Product> getAllProductsFromOrder(Order order) {
        return order.getProducts().stream()
                .map(product -> findProduct(product.getId()))
                .collect(Collectors.toList());
    }
}
