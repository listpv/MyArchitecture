package ru.geekbrains.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.geekbrains.entities.Order;
import ru.geekbrains.repositories.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderMapper implements RowMapper<Order> {

    private final UserRepository userRepository;

    public OrderMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setCode(rs.getString("code"));
        order.setTotalPrice(rs.getBigDecimal("total_price"));
        order.setUser(userRepository.getOne(rs.getLong("user_id")));
        return order;
    }
}
