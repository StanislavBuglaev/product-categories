package com.product_categories.dao;

import com.product_categories.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> findAll();

    Optional<Product> findById(Integer productId);

    Integer create(Product product);

    Integer update(Product product);

    Integer delete(Integer productId);
}
