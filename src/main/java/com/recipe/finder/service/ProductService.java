package com.recipe.finder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.finder.domain.ProductDto;
import com.recipe.finder.entity.Product;
import com.recipe.finder.exception.ProductNotFoundException;
import com.recipe.finder.mapper.ProductMapper;
import com.recipe.finder.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductDao productDao;
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();


    public ProductDto addProduct(String productDto) throws IOException {

        ProductDto mappedProductDto = objectMapper.readValue(productDto, ProductDto.class);
        Product product = productDao.save(productMapper.mapToProduct(mappedProductDto));
        return productMapper.mapToProductDto(product);
    }

    public void deleteProduct(String productDto) throws ProductNotFoundException, JsonProcessingException {
        ProductDto mappedProductDto = objectMapper.readValue(productDto, ProductDto.class);

        Product product = productMapper.mapToProduct(mappedProductDto);
        Product foundProduct = productDao.findById(product.getId()).orElseThrow(ProductNotFoundException::new);
        productDao.deleteById(foundProduct.getId());
    }

    public List<ProductDto> getProducts() {
        List<Product> foundProducts = productDao.findAll();
        List<ProductDto> convertedItems = new ArrayList<>();
        for (Product product : foundProducts) {
            convertedItems.add(productMapper.mapToProductDto(product));
        }
        return convertedItems;
    }

    public ProductDto updateProduct(ProductDto productDto) {
        logger.info("to update product {}", productDto);
        productDao.deleteById(productDto.getId());
        return productMapper.mapToProductDto(addOrUpdateProdudct(productDto));


    }

    private Product addOrUpdateProdudct(ProductDto productDto){
        Product product = productMapper.mapToProduct(productDto);
        if (productDto.getId() == null) {
            productDao.save(productMapper.mapToProduct(productDto));
        } else {
            productDao.deleteById(productDto.getId());
            productDao.save(product);
        }
        logger.info("updated product {}", product);
        return product;
    }

}
