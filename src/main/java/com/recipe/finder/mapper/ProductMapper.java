package com.recipe.finder.mapper;

import com.recipe.finder.domain.ProductDto;
import com.recipe.finder.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductDto mapToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .unit(product.getUnit())
                .quantity(product.getQuantity())
                .build();
    }

    public Product mapToProduct(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .unit(productDto.getUnit())
                .quantity(productDto.getQuantity())
                .build();
    }
}
