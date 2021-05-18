package com.fiedormichal.productOrder.repository;

import com.fiedormichal.productOrder.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
