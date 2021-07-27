package com.product_categories.service;

import com.product_categories.dao.ProductDao;
import com.product_categories.model.Product;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    ProductDao productDao;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    public void shouldCreateProduct() {
        // Given
        //create object
        Product product = new Product();
        product.setProductName("Random");
        product.setProductPrice(300.0);
        Mockito.when(productDao.create(product)).thenReturn(1);

        // When
        Integer id = productService.create(product);

        // Then
        //check
        assertNotNull(id);
    }

    @Test
    public void shouldFindAllProducts() {

        //create object
        Product product = new Product();
        product.setProductName("Random");
        product.setProductPrice(300.0);
        Mockito.when(productDao.create(product)).thenReturn(1);

        Integer id = productService.create(product);

        //find all objects
        Mockito.when(productDao.findAll()).thenReturn(Collections.singletonList(product));
        List<Product> products = productService.findAll();

        //check
        assertNotNull(products);
        Assert.assertTrue(products.size() > 0);
    }


    @Test
    public void shouldUpdateProduct() {

        // given
        //create object
        Product product = new Product();
        product.setProductName("random");
        product.setProductPrice(300.0);
        Mockito.when(productDao.create(product)).thenReturn(1);

        Integer id = productService.create(product);

        assertNotNull(id);

        //find object by id
        Mockito.when(productService.findById(id)).thenReturn(Optional.of(product));
        Optional<Product> productOptional = productService.findById(id);

        //check
        Assertions.assertTrue(productOptional.isPresent());

        //update object with name "random" to object with name "update random"
        productOptional.get().setProductName("update random");
        productOptional.get().setProductPrice(300.0);
        Mockito.when(productDao.update(product)).thenReturn(1);
        int result = productService.update(productOptional.get());

        assertTrue(1 == result);

        //check if the object has been updated
        Optional<Product> updatedProductOptional = productService.findById(id);

        //check
        Assertions.assertTrue(updatedProductOptional.isPresent());
        assertEquals(updatedProductOptional.get().getProductName(), productOptional.get().getProductName());

    }

    @Test
    public void deleteProduct() {
        Product product = new Product();
        product.setProductName("random");
        product.setProductPrice(300.0);
        Mockito.when(productDao.create(product)).thenReturn(1);

        Integer id = productService.create(product);

        Mockito.when(productDao.findAll()).thenReturn(Collections.singletonList(product));
        List<Product> products = productService.findAll();

        assertNotNull(products);

        Integer currentResult = products.size();

        assertTrue(1 == currentResult);

        Integer deleteResult = productService.delete(id);

        assertTrue(currentResult - 1 == deleteResult);
    }

}
