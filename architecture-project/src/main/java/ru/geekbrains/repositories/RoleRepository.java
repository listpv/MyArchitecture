package ru.geekbrains.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Role;
import ru.geekbrains.entities.User;
import ru.geekbrains.mappers.RoleMapper;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RoleMapper roleMapper;

    public void insert(Role role){
        String sql = String.format("insert into roles (name) values ('%s')", role.getName());
        jdbcTemplate.execute(sql);
    }

    public Role findRoleByName(String name){
        String sql = String.format("select * from roles where name = '%s' limit 1", name);
        return jdbcTemplate.queryForObject(sql, roleMapper);
    }

    public List<Role> findUserRole(User user){
//        String sql = String.format("select * from roles where id = (select role_id from users_roles where " +
//                "user_id = %s)", user.getId());
        String sql = String.format("select * from roles join users_roles on roles.id = users_roles.role_id " +
                "where users_roles.user_id = %s", user.getId());
        return jdbcTemplate.query(sql, roleMapper);
    }
}
