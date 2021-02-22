package ru.geekbrains.services;

import org.springframework.stereotype.Service;
import ru.geekbrains.entities.Role;
import ru.geekbrains.repositories.RoleRepository;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    Role findRoleByName(String name){
        return roleRepository.findRoleByName(name);
    }
}
