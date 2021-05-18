package com.fiedormichal.productOrder.repository;

import com.fiedormichal.productOrder.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
