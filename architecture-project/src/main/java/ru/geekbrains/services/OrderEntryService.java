package ru.geekbrains.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.entities.OrderEntry;
import ru.geekbrains.repositories.OrderEntryRepository;

@Service
@RequiredArgsConstructor
public class OrderEntryService {

    private final OrderEntryRepository orderEntryRepository;

    public void insert(OrderEntry orderEntry){
        orderEntryRepository.insert(orderEntry);
    }
}
