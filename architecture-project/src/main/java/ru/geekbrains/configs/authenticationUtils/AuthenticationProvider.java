package ru.geekbrains.configs.authenticationUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.geekbrains.services.UserService;

@Component
@RequiredArgsConstructor
public class AuthenticationProvider {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

}
