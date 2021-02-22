package ru.geekbrains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.geekbrains.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {

//        DataBaseConnection.setConnection();
        SpringApplication.run(StoreApplication.class, args);
//        DataBaseConnection.disconnect();
    }
}
