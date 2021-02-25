package ru.geekbrains.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entities.Order;
import ru.geekbrains.entities.Product;
import ru.geekbrains.entities.User;
import ru.geekbrains.services.*;
import ru.geekbrains.utils.ProductPriceObserver;
import ru.geekbrains.utils.Subject;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequestMapping("/admin")
public class AdminController extends Subject {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final OrderEntryService orderEntryService;

    @PostConstruct
    private void attachObserver(){
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
    @GetMapping("/product/{id}")
    public String allAboutProduct(
            @PathVariable Long id,
            Model model){

        Product product = productService.getOne(id);
        List<User> userList = userService.findByProduct(product);
        model.addAttribute("product", product);
        model.addAttribute("count", orderEntryService.getCountProduct(product));
        model.addAttribute("count_user", userList.size());
        model.addAttribute("count_order", orderService.findByProduct(product).size());
//        model.addAttribute("count_order", orderEntryService.getCountOrderByProduct(product));

        return "product_admin";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/order_products/{id}")
    public String ordersWithProduct(
            @PathVariable Long id,
            Model model){
        Product product = productService.getOne(id);
        System.out.println("Product product  -- " + product);
        List<Order> orders = orderService.findByProduct(product);
//        List<Order> orders = orderService.findByProduct(productService.getOne(id));
        model.addAttribute("orders", orders);
        model.addAttribute("total_price", orderService.getTotalPriceOfAllOrders(orders));
        return "orders";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/user_products/{id}")
    public String usersWithProduct(
            @PathVariable Long id,
            Model model){
        List<User> users = userService.findByProduct(productService.getOne(id));
        model.addAttribute("users", users);
        return "users";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/orders")
    public String orders(
//            @RequestParam Map<String, String> params,
            Model model
    ) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("total_price", orderService.getTotalPriceOfAllOrders(orders));
        return "orders";
    }


    @Secured({"ROLE_ADMIN"})
    @GetMapping("/orders/order/{id}")
    public String oneOrder(
            @PathVariable("id") Long id,
            Model model
    ) {
        Order order = orderService.getOne(id);
        model.addAttribute("order", order);
        model.addAttribute("order_items", order.getOrderEntries());
//        return "redirect:/admin/orders";
        return "order";
    }


    @Secured({"ROLE_ADMIN"})
    @GetMapping("/users")
    public String users(
            Model model
    ) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/user/{id}")
    public String userOrders(
            @PathVariable("id") Long id,
            Model model){
        List<Order> orders = orderService.findOrdersByUser(userService.getOne(id));
        model.addAttribute("orders", orders);
        model.addAttribute("name", userService.getOne(id).getName());
        model.addAttribute("total_price", orderService.getTotalPriceOfAllOrders(orders));
        return "user_orders";
    }

}
