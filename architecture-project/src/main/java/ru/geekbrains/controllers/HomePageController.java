package ru.geekbrains.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.services.CategoryService;
import ru.geekbrains.services.ProductService;
import ru.geekbrains.services.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomePageController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping
    public String main(Model model){
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }
}
