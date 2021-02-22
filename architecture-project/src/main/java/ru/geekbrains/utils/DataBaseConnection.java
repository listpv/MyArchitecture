package ru.geekbrains.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static Connection connection;

    public static void setConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:h2:file:C:\\java\\Architecture\\architecture-project\\db\\demo", "sa", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
