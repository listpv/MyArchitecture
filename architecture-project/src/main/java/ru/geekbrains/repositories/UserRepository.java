package ru.geekbrains.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.User;
import ru.geekbrains.mappers.UserMapper;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public UserRepository(JdbcTemplate jdbcTemplate, UserMapper userMapper, RoleRepository roleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

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


}
