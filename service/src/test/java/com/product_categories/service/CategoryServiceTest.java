package com.product_categories.service;


import com.product_categories.dao.CategoryDao;
import com.product_categories.model.Category;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    CategoryDao categoryDao;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    public void createCategory() {
        // Given
        //create object
        Category category = new Category();
        category.setCategoryName("Random");
        Mockito.when(categoryDao.create(category)).thenReturn(1);

        // When
        Integer id = categoryService.create(category);

        // Then
        //check
        assertNotNull(id);
    }

    @Test
    public void findAllCategories() {

        //create object
        Category category = new Category();;
        category.setCategoryName("Randomazer");

        Mockito.when(categoryDao.findAll()).thenReturn(Collections.singletonList(category));


        List<Category> categories = categoryService.findAll();

        //check
        assertNotNull(categories);
        Assert.assertTrue(categories.size() > 0);
    }


    @Test
    public void updateCategory() {

        // given
        //create object
        Category category = new Category();
        category.setCategoryName("random");
        Mockito.when(categoryDao.create(category)).thenReturn(1);

        Integer id = categoryService.create(category);

        assertNotNull(id);

        //find object by id
        Mockito.when(categoryService.findById(id)).thenReturn(Optional.of(category));
        Optional<Category> categoryOptional = categoryService.findById(id);

        //check
        Assertions.assertTrue(categoryOptional.isPresent());

        //update object with name "random" to object with name "update random"
        categoryOptional.get().setCategoryName("update random");
        Mockito.when(categoryDao.update(category)).thenReturn(1);
        int result = categoryService.update(categoryOptional.get());

        assertTrue(1 == result);

        //check if the object has been updated
        Optional<Category> updatedCategoryOptional = categoryService.findById(id);

        //check
        Assertions.assertTrue(updatedCategoryOptional.isPresent());
        assertEquals(updatedCategoryOptional.get().getCategoryName(), categoryOptional.get().getCategoryName());

    }

    @Test
    public void deleteCategory() {
        Category category = new Category();
        category.setCategoryName("random");
        Mockito.when(categoryDao.create(category)).thenReturn(1);

        Integer id = categoryService.create(category);

        Mockito.when(categoryDao.findAll()).thenReturn(Collections.singletonList(category));
        List<Category> categories = categoryService.findAll();

        assertNotNull(categories);

        Integer currentResult = categories.size();

        assertTrue(1 == currentResult);

        Integer deleteResult = categoryService.delete(id);

        assertTrue(currentResult - 1 == deleteResult);
    }

}
