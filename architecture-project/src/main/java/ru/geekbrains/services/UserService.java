package ru.geekbrains.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.data.UserAdminData;
import ru.geekbrains.data.UserData;
import ru.geekbrains.entities.Role;
import ru.geekbrains.entities.User;
import ru.geekbrains.repositories.UserRepository;
import ru.geekbrains.repositories.UserRoleRepository;
import ru.geekbrains.utils.UserBuilder;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserRoleRepository userRoleRepository;

//    public UserService(UserRepository userRepository
//            , PasswordEncoder passwordEncoder
//    ) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }


//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
//                       RoleService roleService, UserRoleRepository userRoleRepository) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.roleService = roleService;
//        this.userRoleRepository = userRoleRepository;
//    }

    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public User findByName(String name){
        return userRepository.findByName(name);
    }

    public User getCurrentUser(){
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        return findByUsername(principal.getName());
    }

    public User getOne(Long id){
        return userRepository.getOne(id);
    }

//    public User createUser(UserData userData){
//        User user = new User();
//        user.setName(userData.getName());
//        user.setUsername(userData.getUsername());
//        user.setPassword(passwordEncoder.encode(userData.getPassword()));
//        user.setEmail(userData.getEmail());
//        user.setPhone(userData.getPhone());
//        userRepository.insert(user);
//        return user;
//    }

//    public User createUser(UserAdminData userAdminData){
//        User user = new User();
//        user.setName(userAdminData.getName());
//        user.setUsername(userAdminData.getUsername());
//        user.setPassword(passwordEncoder.encode(userAdminData.getPassword()));
//        user.setEmail(userAdminData.getEmail());
//        user.setPhone(userAdminData.getPhone());
//        userRepository.insert(user);
//        if(userAdminData.getRoles() != null) {
//            for (String o : userAdminData.getRoles()) {
//                userRoleRepository.insert(user, roleService.findRoleByName(o));
//            }
//        }
//        return user;
//    }


    ///  Испрользую UserBuilder
    public User createUser(UserData userData){
        User user = (new UserBuilder())
                .setName(userData.getName())
                .setUserName(userData.getUsername())
                .setPassword(passwordEncoder.encode(userData.getPassword()))
                .setEmail(userData.getEmail())
                .setPhone(userData.getPhone())
                .build();
        userRepository.insert(user);
        return user;
    }

    ///  Испрользую UserBuilder
    public User createUser(UserAdminData userAdminData){
        User user = (new UserBuilder())
                .setName(userAdminData.getName())
                .setUserName(userAdminData.getUsername())
                .setPassword(passwordEncoder.encode(userAdminData.getPassword()))
                .setEmail(userAdminData.getEmail())
                .setPhone(userAdminData.getPhone())
                .build();
        userRepository.insert(user);
        if(userAdminData.getRoles() != null) {
            for (String o : userAdminData.getRoles()) {
                userRoleRepository.insert(user, roleService.findRoleByName(o));
            }
        }
        return user;
    }

    public void authenticateUser(User user){
        List<Role> roles = user.getRoles().stream().distinct().collect(Collectors.toList());
        List<GrantedAuthority> authorities = roles.stream()
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
