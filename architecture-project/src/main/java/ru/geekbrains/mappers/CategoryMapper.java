package ru.geekbrains.mappers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.geekbrains.entities.Category;
import ru.geekbrains.entities.Product;
import ru.geekbrains.utils.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper implements RowMapper<Category> {

//    private JdbcTemplate jdbcTemplate;
//
//    public CategoryMapper(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public void insert(Category category) throws SQLException {
//        String sql = String.format("insert into categories (code, name) values('%s', '%s')", category.getCode(), category.getName() );
//        jdbcTemplate.execute(sql);
//    }

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong("id"));
        category.setCode(rs.getString("code"));
        category.setName(rs.getString("name"));
        return category;
    }
}
