package ru.geekbrains.services;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entities.Order;
import ru.geekbrains.entities.OrderEntry;
import ru.geekbrains.entities.User;
import ru.geekbrains.repositories.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private CartService cartService;
    private UserService userService;
    private OrderEntryService orderEntryService;

    public OrderService(OrderRepository orderRepository, CartService cartService,
                        UserService userService, OrderEntryService orderEntryService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userService = userService;
        this.orderEntryService = orderEntryService;
    }

    @Transactional
    public Order createOrder() {
        Order order = new Order();
        order.setCode(UUID.randomUUID().toString().substring(0,4));
        User user = userService.getCurrentUser();
        System.out.println("createOrder user = " + user);
        order.setUser(user);
        order.setTotalPrice(cartService.getTotalPrice());
        order.setOrderEntries(cartService.getOrderEntries());
        orderRepository.insert(order);
        Order orderBD = orderRepository.findOrderByCode(order.getCode());
        cartService.getOrderEntries().stream().forEach(orderEntry -> {
            orderEntry.setOrder(orderBD);
        });
        for(OrderEntry o : cartService.getOrderEntries()){
            orderEntryService.insert(o);
        }
        cartService.clearCart();
        return order;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

//    @Transactional
//    public void remove(Long id) {
//        orderRepository.deleteById(id);
//    }
}
