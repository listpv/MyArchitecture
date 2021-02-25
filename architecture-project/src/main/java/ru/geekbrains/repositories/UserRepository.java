package ru.geekbrains.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Product;
import ru.geekbrains.entities.User;
import ru.geekbrains.mappers.UserMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public void insert(User user){
        String sql = String.format("insert into users (username, name, password, phone, email, address, birthday) " +
                        "values ('%s', '%s', '%s', '%s', '%s', '%s', CURDATE())",
                user.getUsername(), user.getName(), user.getPassword(), user.getPhone(), user.getEmail(),
                user.getAddress()
//                , user.getBirthday()
        );
       jdbcTemplate.execute(sql);
    }

    public User findByUserName(String username){
        String sql = String.format("select * from users where username = '%s' limit 1", username);
        User user = jdbcTemplate.queryForObject(sql, userMapper);
        if(user != null){
            user.setRoles(roleRepository.findUserRole(user));
        }
        return user;
    }

    public User findByName(String name){
        String sql = String.format("select * from users where name = '%s' limit 1", name);
        User user = jdbcTemplate.queryForObject(sql, userMapper);
        if(user != null){
            user.setRoles(roleRepository.findUserRole(user));
        }
        return user;
    }

    public User getOne(Long id){
        String sql = String.format("select * from users where id = %s limit 1", id);
        User user = jdbcTemplate.queryForObject(sql, userMapper);
        if(user != null){
            user.setRoles(roleRepository.findUserRole(user));
        }
        return user;
    }

    public List<User> findAll(){
        String sql = String.format("select * from users");
        return jdbcTemplate.query(sql, userMapper);
    }

    public List<User> findByProduct(Product product) {
        String sql = String.format("select * from users join orders on users.id = orders.user_id join order_items " +
                "on orders.id = order_items.order_id where order_items.product_id = %s", product.getId());
        return jdbcTemplate.query(sql, userMapper);
    }

}

