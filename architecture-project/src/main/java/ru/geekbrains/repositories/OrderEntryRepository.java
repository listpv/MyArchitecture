package ru.geekbrains.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.OrderEntry;

@Repository
public class OrderEntryRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderEntryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert (OrderEntry orderEntry){
        String sql = String.format("insert into order_items (price_per_product, quantity, price, order_id, product_id)" +
                " values (%s, %s, %s, %s, %s)", orderEntry.getBasePrice(), orderEntry.getQuantity(),
                orderEntry.getTotalPrice(), orderEntry.getOrder().getId(), orderEntry.getProduct().getId());
        jdbcTemplate.execute(sql);
    }
}
