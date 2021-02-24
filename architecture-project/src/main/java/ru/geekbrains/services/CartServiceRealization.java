package ru.geekbrains.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.geekbrains.entities.OrderEntry;
import ru.geekbrains.entities.Product;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Setter
@Getter
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceRealization {

    private List<OrderEntry> orderEntries;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private Integer totalQuantity = 0;

    @PostConstruct
    public void init() {
        orderEntries = new ArrayList<>();
    }

    public void addOneAndUpdate(Product p) {
        for (OrderEntry orderEntry : orderEntries) {
            if (orderEntry.getProduct().getId().equals(p.getId())) {
                orderEntry.setQuantity(orderEntry.getQuantity() + 1);
                recalculate();
                return;
            }
        }
        orderEntries.add(new OrderEntry(p));
        recalculate();
    }

    public void removeOneAndUpdate(Long productId) {
        Iterator<OrderEntry> iterator = getOrderEntryIterator();
        while (iterator.hasNext()) {
            OrderEntry orderEntry = iterator.next();
            if (orderEntry.getProduct().getId().equals(productId)) {
                if(orderEntry.getQuantity() - 1 == 0){
                    iterator.remove();
                } else {
                    orderEntry.setQuantity(orderEntry.getQuantity() - 1);
                }
                recalculate();
                return;
            }
        }
    }

    public void removeAll(Long productId) {
        Iterator<OrderEntry> iterator = getOrderEntryIterator();
        while (iterator.hasNext()) {
            OrderEntry o = iterator.next();
            if (o.getProduct().getId().equals(productId)) {
                iterator.remove();
                recalculate();
                return;
            }
        }
    }

    public void recalculate() {
        totalPrice =BigDecimal.ZERO;
        totalQuantity = 0;
        for (OrderEntry orderEntry : orderEntries) {
            BigDecimal price = orderEntry.getBasePrice().multiply(new BigDecimal (orderEntry.getQuantity()));
            orderEntry.setTotalPrice(price);
            totalPrice = totalPrice.add(price);
            totalQuantity += orderEntry.getQuantity();
        }
    }

    public void clearCart() {
        orderEntries = new ArrayList<>();
        recalculate();
    }

    public Iterator<OrderEntry> getOrderEntryIterator(){
        return orderEntries.iterator();
    }

}
