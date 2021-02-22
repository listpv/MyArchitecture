package ru.geekbrains.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.services.CategoryService;
import ru.geekbrains.services.ProductService;
import ru.geekbrains.services.UserService;

@Controller
@RequestMapping("/")
public class HomePageController {

    private CategoryService categoryService;
    private ProductService productService;
    private UserService userService;

    public HomePageController(CategoryService categoryService, ProductService productService, UserService userService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public String main(Model model){
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }
}
