package ru.geekbrains.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Role;
import ru.geekbrains.entities.User;
import ru.geekbrains.mappers.RoleMapper;
import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class UserRoleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

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
