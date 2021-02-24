package ru.geekbrains.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.entities.Order;
import ru.geekbrains.entities.Product;
import ru.geekbrains.services.CartServiceRealization;
import ru.geekbrains.services.ProductService;
import ru.geekbrains.services.WrapProxyOrderService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final CartServiceRealization cartServiceRealization;
//    private final OrderService orderService;
    private final WrapProxyOrderService wrapProxyOrderService;

    @GetMapping
    public String showCartPage() {
        return "cart";
    }

    @GetMapping("/add/{product_id}")
    public void addToCart(
            @PathVariable(name = "product_id") Long productId
            , HttpServletRequest request
            , HttpServletResponse response) throws IOException {
        Product product = productService.getOne(productId);
        cartServiceRealization.addOneAndUpdate(product);
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/removeOne/{product_id}")
    public String removeOne(@PathVariable(name = "product_id") Long productId) {
        cartServiceRealization.removeOneAndUpdate(productId);
        return "redirect:/cart";
    }

    @GetMapping("/removeAll/{product_id}")
    public String removeAll(@PathVariable(name = "product_id") Long productId) {
        cartServiceRealization.removeAll(productId);
        return "redirect:/cart";
    }


    @GetMapping("/clearCart")
    public String clearCart(
            Model model
    ) {
        cartServiceRealization.clearCart();
        return "cart";
    }

    @GetMapping("/createOrder")
    public String createOrder(
            Model model
    ) {
//        Order order = orderService.createOrder();
        Order order = wrapProxyOrderService.createOrder();
        model.addAttribute("order", order);
        return "success";
    }
}
