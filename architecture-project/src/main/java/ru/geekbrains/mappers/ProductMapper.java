package ru.geekbrains.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.geekbrains.entities.Product;
import ru.geekbrains.repositories.CategoryRepository;

import java.sql.*;

@Component
@RequiredArgsConstructor
public class ProductMapper implements RowMapper<Product> {

    private final CategoryRepository categoryRepository;

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setTitle(rs.getString("title"));
        product.setBrandName(rs.getString("brand_name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setImage(rs.getString("image"));
        product.setCategory(categoryRepository.getOne(rs.getLong("category_id")));
        return product;
    }
}
