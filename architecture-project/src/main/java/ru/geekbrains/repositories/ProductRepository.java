package ru.geekbrains.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Product;
import ru.geekbrains.mappers.ProductMapper;

import java.util.*;

@Repository
@Log
@RequiredArgsConstructor
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProductMapper productMapper;

    private final Map<Long, Product> identityMap = new HashMap<>();

//    private static final Logger logger = LoggerFactory.getLogger(Product.class);

    public void insert(Product product){
        identityMap.remove(product.getId());
        log.info("КОЛИЧЕСТВО ПРОДУКТОВ УМЕНЬШИЛОСЬ -- " + identityMap.size());
        String sql = String.format("insert into products (title, brand_name, image, price, category_id) " +
                        "values('%s', '%s', '%s', %s, %s)", product.getTitle(),
                product.getBrandName(), product.getImage(), product.getPrice(), product.getCategory().getId());
        jdbcTemplate.execute(sql);
    }

//    public List<Product> findAll(){
//        String sql = String.format("select * from products");
//        return jdbcTemplate.query(sql, productMapper);
//    }

    public List<Product> findAll(){
        StringBuilder stringForSql = new StringBuilder("");
        if(identityMap.size() != 0){
            stringForSql.append(" where ");
            for(Long i : identityMap.keySet()){
                stringForSql.append("id != ").append(i).append(" and ");
            }
//            System.out.println("STRING STRING_FOR_SQL --  " + stringForSql);
            stringForSql.delete(stringForSql.length() - 5, stringForSql.length());
        }
        String sql = String.format("select * from products" + stringForSql);
//        System.out.println("STRING SQL --  " + sql);
        List<Product> productList = jdbcTemplate.query(sql, productMapper);
        if(productList.size() != 0){
            for(Product p : productList){
                identityMap.put(p.getId(), p);
                log.info("КОЛИЧЕСТВО ПРОДУКТОВ В PRODUCT_IDENTITY_MAP УВЕЛИЧИЛОСЬ -- " + identityMap.size());
            }
        }

        List<Product> resultList = new ArrayList<>();
        for(Long i : identityMap.keySet()){
            resultList.add(identityMap.get(i));
        }
        return resultList;
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
                log.info("КОЛИЧЕСТВО ПРОДУКТОВ В PRODUCT_IDENTITY_MAP УВЕЛИЧИЛОСЬ -- " + identityMap.size());
            }
        }
        return product;
    }

    public void update(Product product){
        identityMap.remove(product.getId());
        log.info("КОЛИЧЕСТВО ПРОДУКТОВ УМЕНЬШИЛОСЬ -- " + identityMap.size());
        String sql = String.format("update products set title = '%s', brand_name = '%s', image = '%s'," +
                " price = %s, category_id = %s where id = %s", product.getTitle(), product.getBrandName(), product.getImage(),
                 product.getPrice(), product.getCategory().getId(), product.getId());
        jdbcTemplate.execute(sql);
    }



}
