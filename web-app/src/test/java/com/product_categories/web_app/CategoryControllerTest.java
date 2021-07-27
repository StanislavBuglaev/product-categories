package com.product_categories.web_app;

import com.product_categories.model.Category;
import com.product_categories.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CategoryController.class)
@ContextConfiguration(locations = {"applicationContext-test.xml"})
public class CategoryControllerTest {


    @Mock
    CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;
    @Captor
    private ArgumentCaptor<Category> captor;

    //Create object Category method
    private Category createCategory(int categoryId, String categoryName) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);
        return category;
    }


    @Test
    public void categoriesPage() throws Exception {
        //create objects
        Category category1 = createCategory(1, "random1");
        Category category2 = createCategory(2, "random2");

        //mock service conf
       // when(categoryService.findAll()).thenReturn(Arrays.asList(category1, category2));

        //mockMVC
        mockMvc.perform(
                        //request address
                        MockMvcRequestBuilders.get("/categories")
                        //check
                ).andDo(MockMvcResultHandlers.print())
                //status 200
                .andExpect(MockMvcResultMatchers.status().isOk())
                //type
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                // name of view
                .andExpect(view().name("categories"))

                .andExpect(model().attribute("categories", hasItem(
                        allOf(
                                hasProperty("categoryId", is(category1.getCategoryId())),
                                hasProperty("categoryName", is(category1.getCategoryName()))
                        )
                )))
                .andExpect(model().attribute("categories", hasItem(
                        allOf(
                                hasProperty("categoryId", is(category2.getCategoryId())),
                                hasProperty("categoryName", is(category2.getCategoryName()))
                        )
                )));
    }

    @Test
    public void goToAddCategoryPage() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/addCategory")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("addCategory"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("category", isA(Category.class)));
    }

    @Test
    public void AddCategory() throws Exception {
        String testName = "random";
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/addCategory")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("categoryName", testName)
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/categories"))
                .andExpect(redirectedUrl("/categories"));

        verify(categoryService).create(captor.capture());

        Category ctgr = captor.getValue();
        assertEquals(testName, ctgr.getCategoryName());
    }

    @Test
    public void goToEditCategoryPageById() throws Exception {
        Category category = createCategory(1, "random");
        when(categoryService.findById(any())).thenReturn(Optional.of(category));
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/editCategory/" + category.getCategoryId())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("editCategory"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("category",
                        hasProperty("categoryId", is(category.getCategoryId()))))
                .andExpect(model().attribute("category",
                        hasProperty("categoryName", is(category.getCategoryName()))));
    }


    @Test
    public void updateCategory() throws Exception {

        String testName = "random";
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/editCategory/1")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("categoryId", "1")
                                .param("categoryName", testName)
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/categories"))
                .andExpect(redirectedUrl("/categories"));

        verify(categoryService).update(captor.capture());

        Category ctgr = captor.getValue();
        assertEquals(testName, ctgr.getCategoryName());
    }

    @Test
    public void deleteCategory() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/categories/1/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/categories"))
                .andExpect(redirectedUrl("/categories"));

        verify(categoryService).delete(1);
    }

}
