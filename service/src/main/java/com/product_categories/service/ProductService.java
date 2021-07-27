package com.product_categories.service;

import com.product_categories.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    Optional<Product> findById(Integer flatId);

    Integer create(Product product);

    Integer update(Product product);

    Integer delete(Integer productId);
}
