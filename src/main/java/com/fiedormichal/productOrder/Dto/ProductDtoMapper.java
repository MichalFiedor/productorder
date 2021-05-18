package com.fiedormichal.productOrder.Dto;

import com.fiedormichal.productOrder.model.Product;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ProductDtoMapper {

    private ProductDtoMapper(){

    }

    public static List<ProductDto> mapToDtos(List<Product>products){
        return products.stream()
                .map(product -> mapToDto(product))
                .collect(Collectors.toList());
    }

    public static ProductDto mapToDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
