package ru.geekbrains.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Category;
import ru.geekbrains.entities.Product;
import ru.geekbrains.mappers.CategoryMapper;
import ru.geekbrains.mappers.ProductMapper;

import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CategoryMapper categoryMapper;

    public CategoryRepository(JdbcTemplate jdbcTemplate, CategoryMapper categoryMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryMapper = categoryMapper;
    }

    public Category findCategoryByName(String name) throws SQLException {
        String sql = String.format("select * from categories where name = '%s' limit 1", name);
        return jdbcTemplate.queryForObject(sql, categoryMapper);
    }

    public void insert(Category category) throws SQLException {
        String sql = String.format("insert into categories (code, name) values('%s', '%s')", category.getCode(), category.getName() );
        jdbcTemplate.execute(sql);
    }

//    public void update(String code, List<Product> productList){
//        String sql = String.format("update categories set productList = %s where code = '%s'", productList, code);
//        jdbcTemplate.execute(sql);
//    }

    public List<Category> findAll(){
        String sql = String.format("select * from categories");
        return jdbcTemplate.query(sql, categoryMapper);
    }

    public Category getOne(Long id){
        String sql = String.format("select * from categories where id = %s", id);
        return jdbcTemplate.queryForObject(sql,categoryMapper);
    }

    public Category getCategoryByCode(String code){
        String sql = String.format("select * from categories where code = '%s' limit 1", code);
        return jdbcTemplate.queryForObject(sql, categoryMapper);
    }

}
