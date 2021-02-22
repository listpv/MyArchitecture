package ru.geekbrains.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entities.Order;
import ru.geekbrains.entities.Product;
import ru.geekbrains.entities.User;
import ru.geekbrains.services.CategoryService;
import ru.geekbrains.services.OrderService;
import ru.geekbrains.services.ProductService;
import ru.geekbrains.services.UserService;
import ru.geekbrains.utils.ProductPriceObserver;
import ru.geekbrains.utils.Subject;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequestMapping("/admin")
public class AdminController extends Subject {

    private ProductService productService;
    private OrderService orderService;
    private UserService userService;
    private CategoryService categoryService;

    public AdminController(ProductService productService, OrderService orderService,
                           UserService userService, CategoryService categoryService) {
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
        this.categoryService = categoryService;
        attach(new ProductPriceObserver());
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/products")
    public String showAllProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/product/add")
    public String addProduct(
            Model model
    ) {
        model.addAttribute("product", new Product());
        return "product_add_form";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/product/add")
    public String addProduct(
            @Valid @ModelAttribute Product product,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "product_add_form";
        }
        product.setCategory(categoryService.getCategoryByCode("other"));
        productService.insert(product);
        return "redirect:/admin/products";
    }


    @Secured({"ROLE_ADMIN"})
    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product p = productService.getOne(id);
        model.addAttribute("product", p);
        return "product_edit_form";
    }


    @Secured({"ROLE_ADMIN"})
    @PostMapping("/products/edit")
    public String showEditForm(@ModelAttribute Product product) {
        productService.update(product);
        notify(product.getPrice());
        return "redirect:/admin/products";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/orders")
    public String orders(
            @RequestParam Map<String, String> params,
            Model model
    ) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }


//    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
//    @GetMapping("/orders/remove/{id}")
//    public String remove(
//            @PathVariable("id") Long id,
//            Model model
//    ) {
//        orderService.remove(id);
//        return "redirect:/orders";
//    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/users")
    public String users(
            Model model
    ) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }
}
