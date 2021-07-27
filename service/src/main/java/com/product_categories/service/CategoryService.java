package com.product_categories.service;

import com.product_categories.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Optional<Category> findById(Integer categoryId);

    Integer create(Category category);

    Integer update(Category category);

    Integer delete(Integer categoryId);
}
