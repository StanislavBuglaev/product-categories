package com.product_categories.web_app;

import com.product_categories.model.Category;
import com.product_categories.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
@WebAppConfiguration
public class CategoryControllerTest {


    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /*//In case you need it
    private Category createCategory(int categoryId, String categoryName) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);
        return category;
    }*/

    @Test
    public void goToCategoriesPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("categories"));
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
    public void goToEditCategoryPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/editCategory/2")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("editCategory"))
                .andExpect(model().attribute("isNew", is(false)));
    }


    @Test
    public void deleteCategory() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/1")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/categories"))
                .andExpect(redirectedUrl("/categories"));
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
    }
}
