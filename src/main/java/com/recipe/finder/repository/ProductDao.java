package com.recipe.finder.repository;

import com.recipe.finder.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao extends CrudRepository<Product, Long> {

    @Override
    Product save(Product product);

    void deleteById(Long id);

    void deleteByName(String name);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    Product findByName(String name);

}
