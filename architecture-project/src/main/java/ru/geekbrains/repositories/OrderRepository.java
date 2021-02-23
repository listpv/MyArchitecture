package ru.geekbrains.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Order;
import ru.geekbrains.mappers.OrderMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final OrderMapper orderMapper;

    public List<Order> findAll(){
        String sql = String.format("select * from orders");
        return jdbcTemplate.query(sql, orderMapper);
    }

    public Order findOrderByCode(String code){
        String sql = String.format("select * from orders where code = '%s'", code);
        return jdbcTemplate.queryForObject(sql, orderMapper);
    }

    public void insert(Order order){
        String sql = String.format("insert into orders (code, user_id, total_price) " +
                        "values ('%s', %s, %s)",
               order.getCode(), order.getUser().getId(), order.getTotalPrice());
        jdbcTemplate.execute(sql);
    }
}
