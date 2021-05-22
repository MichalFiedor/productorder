package com.fiedormichal.productOrder.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_product")
    private List<Product> products;
    private BigDecimal totalCost;
    private LocalDateTime orderedAt = LocalDateTime.now();

    public void addProducts(List<Product> productsFromOrder) {
        products = new ArrayList<>();
        for (Product product : productsFromOrder) {
            products.add(product);
        }
    }
}