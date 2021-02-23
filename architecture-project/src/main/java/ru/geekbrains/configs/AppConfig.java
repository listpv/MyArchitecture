package ru.geekbrains.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.geekbrains.utils.DateFormatter;

@Configuration
@EnableTransactionManagement
@ComponentScan("ru.geekbrains")
public class AppConfig {

    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }
}
