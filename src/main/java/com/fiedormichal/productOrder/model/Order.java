package com.fiedormichal.productOrder.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany
    @JoinTable(name = "order_product")
    private List<Product>products;
    private BigDecimal totalCost;
    private LocalDateTime orderedAt;

    public Order(){
        this.orderedAt= LocalDateTime.now();
    }
}