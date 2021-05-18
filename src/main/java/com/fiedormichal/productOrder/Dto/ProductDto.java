package com.fiedormichal.productOrder.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {
    private int id;
    private String name;
    private BigDecimal price;

}
