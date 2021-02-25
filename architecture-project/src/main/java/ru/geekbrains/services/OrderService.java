package ru.geekbrains.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entities.Order;
import ru.geekbrains.entities.OrderEntry;
import ru.geekbrains.entities.Product;
import ru.geekbrains.entities.User;
import ru.geekbrains.repositories.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartServiceRealization cartServiceRealization;
    private final UserService userService;
    private final OrderEntryService orderEntryService;

    @Transactional
    public Order createOrder() {
        Order order = new Order();
        order.setCode(UUID.randomUUID().toString().substring(0,4));
        User user = userService.getCurrentUser();
        System.out.println("createOrder user = " + user);
        order.setUser(user);
        order.setTotalPrice(cartServiceRealization.getTotalPrice());
        order.setOrderEntries(cartServiceRealization.getOrderEntries());
        orderRepository.insert(order);
        Order orderBD = orderRepository.findOrderByCode(order.getCode());
        cartServiceRealization.getOrderEntries().stream().forEach(orderEntry -> {
            orderEntry.setOrder(orderBD);
        });
        for(OrderEntry o : cartServiceRealization.getOrderEntries()){
            orderEntryService.insert(o);
        }
        cartServiceRealization.clearCart();
        return order;
    }

    public List<Order> findAll() {
        return orderEntryService.addOrderEntries(orderRepository.findAll());
    }

    public Order getOne(Long id){
        return orderEntryService.addOrderEntriesForOne(orderRepository.getOne(id));
    }

    public List<Order> findOrdersByUser(User user){
        return orderRepository.findOrdersByUser(user);
    }

    public BigDecimal getTotalPriceOfAllOrders(List<Order> orderList){
        return orderRepository.getTotalPriceOfAllOrders(orderList);
    }

    public List<Order> findByProduct(Product product){
        return orderEntryService.addOrderEntries(orderRepository.findByProduct(product));
    }

//    @Transactional
//    public void remove(Long id) {
//        orderRepository.deleteById(id);
//    }
}
