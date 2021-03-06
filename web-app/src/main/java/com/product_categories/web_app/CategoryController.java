package com.product_categories.web_app;

import com.product_categories.model.Category;
import com.product_categories.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping(value = "/categories")
    public final String categories(Model model) {

        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    @GetMapping(value = "/addCategory")
    public final String gotoAddCategoryPage(Model model) {

        model.addAttribute("isNew", true);
        model.addAttribute("category", new Category());
        return "addCategory";
    }

    @PostMapping(value = "/addCategory")
    public String addCategory(Category category) {
        categoryService.create(category);
        return "redirect:/categories";
    }

    @GetMapping(value = "/editCategory/{id}")
    public final String gotoEditCategoryPage(@PathVariable Integer id, Model model){

        Optional<Category> optionalCategory = categoryService.findById(id);
        if(optionalCategory.isPresent()){
            model.addAttribute("isNew", false);
            model.addAttribute("category", optionalCategory.get());
            model.addAttribute("categoryId", id);
            return "editCategory";
        }else{
            return "redirect:/categories";
        }
    }

    @PostMapping(value = "/editCategory/{id}")
    public String updateCategory(Category category) {
            categoryService.update(category);
            return "redirect:/categories";
        }

    @GetMapping(value = "/categories/{id}")
    public final String deleteCategoryById(@PathVariable Integer id, Model model) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}