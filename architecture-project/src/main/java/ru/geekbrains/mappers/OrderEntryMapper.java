package ru.geekbrains.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.geekbrains.entities.OrderEntry;
import ru.geekbrains.repositories.OrderRepository;
import ru.geekbrains.repositories.ProductRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class OrderEntryMapper implements RowMapper<OrderEntry> {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderEntry orderEntry = new OrderEntry();
        orderEntry.setId(rs.getLong("id"));
        orderEntry.setBasePrice(rs.getBigDecimal("price_per_product"));
        orderEntry.setTotalPrice(rs.getBigDecimal("price"));
        orderEntry.setQuantity(rs.getInt("quantity"));
        orderEntry.setOrder(orderRepository.getOne(rs.getLong("order_id")));
        orderEntry.setProduct(productRepository.getOne(rs.getLong("product_id")));
        return orderEntry;
    }
}
