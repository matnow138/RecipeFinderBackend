package com.recipe.finder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.finder.domain.ProductDto;
import com.recipe.finder.entity.Product;
import com.recipe.finder.mapper.ProductMapper;
import com.recipe.finder.repository.ProductDao;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDao productDao;

    @AfterEach
    void deleteAll() {
        productDao.deleteAll();
    }

    @Test
    public void addProductTest() throws Exception {
        //Given
        Product product = Product.builder()
                .name("Carrot")
                .quantity(5.0)
                .unit("kg")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();

        ProductDto productDto = productMapper.mapToProductDto(product);
        String json = objectMapper.writeValueAsString(productDto);

        //When & Then

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/v1/product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Carrot")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", Matchers.is(5.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.unit", Matchers.is("kg")))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    public void removeProductTest() throws Exception {
        //Given
        Product product = Product.builder()
                .name("Carrot")
                .quantity(5.0)
                .unit("kg")
                .build();
        Product savedProduct = productDao.save(product);
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = productMapper.mapToProductDto(savedProduct);
        String json = objectMapper.writeValueAsString(productDto);
        //When & Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/v1/product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));
        assertEquals(productDao.findAll(), new ArrayList<>());

    }

    @Test
    public void getProductsTest() throws Exception {
        //Given
        Product product1 = Product.builder()
                .name("Carrot")
                .quantity(5.0)
                .unit("kg")
                .build();
        Product product2 = Product.builder()
                .name("Potato")
                .quantity(5.0)
                .unit("kg")
                .build();
        productDao.save(product1);
        productDao.save(product2);

        //When & Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/v1/product")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.notNullValue())));
    }

    @Test
    public void updateProductTest() throws Exception {
        //Given
        Product product = Product.builder()
                .name("Carrot")
                .quantity(5.0)
                .unit("kg")
                .build();
        Product savedProduct = productDao.save(product);
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = productMapper.mapToProductDto(savedProduct);
        productDto.setQuantity(8.0);
        String json = objectMapper.writeValueAsString(productDto);
        //When && Then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/v1/product/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Carrot")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", Matchers.is(8.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.unit", Matchers.is("kg")))
                .andExpect(MockMvcResultMatchers.status().is(200));
        assertEquals(productDao.findAll().size(), 1);

    }

}
