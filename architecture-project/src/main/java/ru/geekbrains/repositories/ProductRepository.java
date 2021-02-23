package ru.geekbrains.repositories;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Product;
import ru.geekbrains.mappers.ProductMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProductMapper productMapper;

    private final Map<Long, Product> identityMap = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(Product.class);

    public void insert(Product product){
        identityMap.remove(product.getId());
        logger.info("КОЛИЧЕСТВО ПРОДУКТОВ УМЕНЬШИЛОСЬ -- " + identityMap.size());
        String sql = String.format("insert into products (title, brand_name, image, price, category_id) " +
                        "values('%s', '%s', '%s', %s, %s)", product.getTitle(),
                product.getBrandName(), product.getImage(), product.getPrice(), product.getCategory().getId());
        jdbcTemplate.execute(sql);
    }

    public List<Product> findAll(){
        String sql = String.format("select * from products");
        return jdbcTemplate.query(sql, productMapper);
    }

    public List<Product> getProductsByCategory(String code){
        String sql = String.format("select * from products where category_id = " +
                "(select id from categories where code = '%s')", code);
        return jdbcTemplate.query(sql, productMapper);
    }

    public Product getOne(Long id){
        Product product = identityMap.get(id);
        if (product == null) {
            String sql = String.format("select * from products where id = %s", id);
            product = jdbcTemplate.queryForObject(sql, productMapper);
            if (product != null) {
                identityMap.put(id, product);
                logger.info("КОЛИЧЕСТВО ПРОДУКТОВ УВЕЛИЧИЛОСЬ -- " + identityMap.size());
            }
        }
        return product;
    }

    public void update(Product product){
        identityMap.remove(product.getId());
        logger.info("КОЛИЧЕСТВО ПРОДУКТОВ УМЕНЬШИЛОСЬ -- " + identityMap.size());
        String sql = String.format("update products set title = '%s', brand_name = '%s', image = '%s'," +
                " price = %s, category_id = %s where id = %s", product.getTitle(), product.getBrandName(), product.getImage(),
                 product.getPrice(), product.getCategory().getId(), product.getId());
        jdbcTemplate.execute(sql);
    }

}
