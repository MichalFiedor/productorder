package com.fiedormichal.productOrder.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="product_table")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Product name can not be blank.")
    private String name;
    @DecimalMin(value = "1.00")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal price;
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Order>orders;
}
