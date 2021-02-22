package ru.geekbrains.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.geekbrains.entities.Order;

@Component
public class WrapProxyOrderService {

    private final OrderService orderService;

    private int quantityOrder;
    private static final Logger logger = LoggerFactory.getLogger(WrapProxyOrderService.class);


    public WrapProxyOrderService(OrderService orderService) {
        this.orderService = orderService;
        this.quantityOrder = 0;
    }

    public Order createOrder(){
        logger.info("Во время сессии сделано " + ++quantityOrder + " заказов.");
        return orderService.createOrder();
    }
}
