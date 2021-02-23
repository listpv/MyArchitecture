package ru.geekbrains.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.geekbrains.entities.Category;
import java.sql.*;

@Component
public class CategoryMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong("id"));
        category.setCode(rs.getString("code"));
        category.setName(rs.getString("name"));
        return category;
    }
}
