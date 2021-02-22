package ru.geekbrains.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.services.CategoryService;
import ru.geekbrains.services.ProductService;

@Controller
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/{code}")
    public String getOneProductById(@PathVariable String code, Model model){
        model.addAttribute("products", productService.getProductsByCategory(code));
        model.addAttribute("categories", categoryService.getCategoryByCode(code));
        return "index";
    }
}
