package ru.geekbrains.services;

import org.springframework.stereotype.Service;
import ru.geekbrains.entities.OrderEntry;
import ru.geekbrains.repositories.OrderEntryRepository;

@Service
public class OrderEntryService {

    private OrderEntryRepository orderEntryRepository;

    public OrderEntryService(OrderEntryRepository orderEntryRepository) {
        this.orderEntryRepository = orderEntryRepository;
    }

    public void insert(OrderEntry orderEntry){
        orderEntryRepository.insert(orderEntry);
    }
}
