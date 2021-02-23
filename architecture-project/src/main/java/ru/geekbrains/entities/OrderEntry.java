package ru.geekbrains.entities;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderEntry{

    private Long id;

    private int quantity;

    private BigDecimal basePrice;

    private BigDecimal totalPrice;

    private Product product;

    private Order order;

    public OrderEntry() {
    }

    public OrderEntry(Product product) {
        this.product = product;
        this.quantity = 1;
        this.basePrice = product.getPrice();
        this.totalPrice = product.getPrice();
    }

    public OrderEntry(Product product, Order order) {
        this.product = product;
        this.order = order;
        this.quantity = 1;
        this.basePrice = product.getPrice();
        this.totalPrice = product.getPrice();
        order.getOrderEntries().add(this);
    }

}
