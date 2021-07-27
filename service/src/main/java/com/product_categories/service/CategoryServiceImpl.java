package com.product_categories.service;

import com.product_categories.model.Category;
import com.product_categories.dao.CategoryDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Optional<Category> findById(Integer categoryId) {
        return categoryDao.findById(categoryId);
    }

    @Override
    public Integer create(Category category) {
        return categoryDao.create(category);
    }

    @Override
    public Integer update(Category category) {
        return categoryDao.update(category);
    }

    @Override
    public Integer delete(Integer categoryId) {
        return categoryDao.delete(categoryId);
    }
}
