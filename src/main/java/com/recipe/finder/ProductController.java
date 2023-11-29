package com.recipe.finder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.recipe.finder.domain.ProductDto;
import com.recipe.finder.exception.ProductNotFoundException;
import com.recipe.finder.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ProductDto> addProduct(@RequestBody String productDto) throws IOException {
        return ResponseEntity.ok().body(productService.addProduct(productDto));
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Void> deleteProduct(@RequestBody String productDto) throws ProductNotFoundException, JsonProcessingException {
        productService.deleteProduct(productDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok().body(productService.getProducts());
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok().body(productService.updateProduct(productDto));
    }


}
