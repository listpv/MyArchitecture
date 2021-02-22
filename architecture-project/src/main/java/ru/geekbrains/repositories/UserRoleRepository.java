package ru.geekbrains.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Role;
import ru.geekbrains.entities.User;
import ru.geekbrains.mappers.RoleMapper;
import ru.geekbrains.mappers.UserRoleMapper;

import java.util.Collection;

@Repository
public class UserRoleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public UserRoleRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository, RoleRepository roleRepository, RoleMapper roleMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public void insert(User user, Role role){
        String sql = String.format("insert into users_roles (user_id, role_id) values (%s, %s)",
                userRepository.findByName(user.getName()).getId(),
                roleRepository.findRoleByName(role.getName()).getId()
        );
        jdbcTemplate.execute(sql);
    }

    public Collection<Role> findUserRole(User user){
        String sql = String.format("select * from users_roles where user_id = %s", user.getId());
        return jdbcTemplate.query(sql, roleMapper);
    }
}
