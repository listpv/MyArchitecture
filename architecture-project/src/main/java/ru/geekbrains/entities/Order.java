package ru.geekbrains.entities;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Order{

    private Long id;

    private String code;

    private User user;

    private BigDecimal totalPrice;

    private List<OrderEntry> orderEntries = new ArrayList<>();

}
