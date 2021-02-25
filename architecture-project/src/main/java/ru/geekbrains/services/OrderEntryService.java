package ru.geekbrains.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.entities.Order;
import ru.geekbrains.entities.OrderEntry;
import ru.geekbrains.entities.Product;
import ru.geekbrains.repositories.OrderEntryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderEntryService {

    private final OrderEntryRepository orderEntryRepository;

    public void insert(OrderEntry orderEntry){
        orderEntryRepository.insert(orderEntry);
    }

    public List<OrderEntry> findByOrder(Order order){
        return orderEntryRepository.findByOrder(order);
    }

    public int getCountProduct(Product product){
        return orderEntryRepository.getCountProduct(product);
    }

    public List<OrderEntry> findByProduct(Product product){
        return orderEntryRepository.findByProduct(product);
    }

    public int getCountOrderByProduct(Product product){
        return orderEntryRepository.getCountOrderByProduct(product);
    }

    public List<Order> addOrderEntries(List<Order> orderList){
        return orderEntryRepository.addOrderEntries(orderList);
    }

    public Order addOrderEntriesForOne(Order order){
       return orderEntryRepository.addOrderEntriesForOne(order);
    }

}
