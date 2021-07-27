package com.product_categories.dao;

import com.product_categories.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    List<Category> findAll();

    Optional<Category> findById(Integer categoryId);

    Integer create(Category category);

    Integer update(Category category);

    Integer delete(Integer categoryId);
}
