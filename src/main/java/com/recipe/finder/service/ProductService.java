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
        ProductDto productDto1 = productMapper.mapToProductDto(product);
        return productDto1;
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
        Product product = productMapper.mapToProduct(productDto);
        if (productDto.getId() == null) {
            productDao.save(productMapper.mapToProduct(productDto));
        } else {
            productDao.deleteById(productDto.getId());
            productDao.save(product);
        }
        logger.info("updated product {}", product);
        return productMapper.mapToProductDto(product);


    }

    public ProductDto getProductByName(String name) {
        Product product = productDao.findByName(name);
        return productMapper.mapToProductDto(product);
    }

    public void deleteProductByName(String name) throws ProductNotFoundException {
        productDao.deleteByName(name);
    }

    public void deleteProductById(Long id) throws ProductNotFoundException {
        productDao.deleteById(id);
    }

    public ProductDto getProductById(Long id) throws ProductNotFoundException {
        Product product = productDao.findById(id).orElseThrow(ProductNotFoundException::new);
        return productMapper.mapToProductDto(product);
    }
}
