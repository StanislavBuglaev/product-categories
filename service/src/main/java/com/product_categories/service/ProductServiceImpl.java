package com.product_categories.service;

import com.product_categories.dao.ProductDao;
import com.product_categories.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Optional<Product> findById(Integer productId) { return productDao.findById(productId); }

    @Override
    public Integer create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Integer update(Product product) {
        return productDao.update(product);
    }

    @Override
    public Integer delete(Integer productId) {
        return productDao.delete(productId);
    }
}
