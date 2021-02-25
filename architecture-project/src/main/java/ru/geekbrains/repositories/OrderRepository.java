package ru.geekbrains.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Order;
import ru.geekbrains.entities.Product;
import ru.geekbrains.entities.User;
import ru.geekbrains.mappers.OrderMapper;

import java.math.BigDecimal;
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

    public Order getOne(Long id){
        String sql = String.format("select * from orders where id = %s", id);
        return jdbcTemplate.queryForObject(sql, orderMapper);
    }

    public List<Order> findOrdersByUser(User user){
        String sql = String.format("select * from orders where user_id = %s", user.getId());
        return jdbcTemplate.query(sql, orderMapper);
    }

    public BigDecimal getTotalPriceOfAllOrders(List<Order> orderList){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(Order order : orderList){
            totalPrice = totalPrice.add(order.getTotalPrice());
        }
        System.out.println("TOTAL_PRICE --  " + totalPrice);
        return totalPrice;
    }

    public List<Order> findByProduct(Product product){
        String sql = String.format("select * from orders join order_items on orders.id = " +
                "order_items.order_id where order_items.product_id = %s", product.getId());
        return jdbcTemplate.query(sql, orderMapper);
    }

}
