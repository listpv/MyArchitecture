package ru.geekbrains.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.entities.Role;
import ru.geekbrains.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    Role findRoleByName(String name){
        return roleRepository.findRoleByName(name);
    }
}
