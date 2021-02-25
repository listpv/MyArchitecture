package ru.geekbrains.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Order;
import ru.geekbrains.entities.OrderEntry;
import ru.geekbrains.entities.Product;
import ru.geekbrains.mappers.OrderEntryMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderEntryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final OrderEntryMapper orderEntryMapper;

    public void insert (OrderEntry orderEntry){
        String sql = String.format("insert into order_items (price_per_product, quantity, price, order_id, product_id)" +
                " values (%s, %s, %s, %s, %s)", orderEntry.getBasePrice(), orderEntry.getQuantity(),
                orderEntry.getTotalPrice(), orderEntry.getOrder().getId(), orderEntry.getProduct().getId());
        jdbcTemplate.execute(sql);
    }

    public List<OrderEntry> findByOrder(Order order){
        String sql = String.format("select * from order_items where order_id = %s", order.getId());
        return jdbcTemplate.query(sql, orderEntryMapper);
    }

    public int getCountProduct(Product product){
        List<OrderEntry> orderEntryList = findByProduct(product);
        int count = 0;
        for(OrderEntry orderEntry : orderEntryList){
            count += orderEntry.getQuantity();
        }
        return count;
    }

    public List<OrderEntry> findByProduct(Product product){
        String sql = String.format("select * from order_items where product_id = %s", product.getId());
        return jdbcTemplate.query(sql, orderEntryMapper);
    }

    public int getCountOrderByProduct(Product product){
        return findByProduct(product).size();
    }

    public List<Order> addOrderEntries(List<Order> orderList){
        for(Order order : orderList){
            order.setOrderEntries(findByOrder(order));
        }
        return orderList;
    }

    public Order addOrderEntriesForOne(Order order){
        order.setOrderEntries(findByOrder(order));
        return order;
    }

}
