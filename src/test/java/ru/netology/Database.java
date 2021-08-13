package ru.netology;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    @SneakyThrows
    public Connection getConnection(){
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
    }

    @SneakyThrows
    public void cleanDB(){
        var connection = getConnection();
        connection.prepareStatement("DROP TABLE IF EXISTS cards;");
        connection.prepareStatement("DROP TABLE IF EXISTS auth_codes;");
        connection.prepareStatement("DROP TABLE IF EXISTS users");
        connection.prepareStatement("DROP TABLE IF EXISTS card_transactions");
    }

    @SneakyThrows
    public String getValidUserID(){
        var connection = getConnection();
        try (var rs = connection.createStatement().executeQuery("SELECT * FROM users");) {
            while (rs.next()) {
                var id = rs.getString("id");
                var login = rs.getString("login");
                if (login.equals("vasya")) {
                    return id;
                }
            }
        }
    }

    @SneakyThrows
    public String getCode() {
        var conn = getConnection();
        try (var rs = conn.createStatement().executeQuery("SELECT * FROM auth_codes")) {
            while (rs.next()) {
                var code = rs.getString("code");
                var user_id = rs.getString("user_id");
                if (user_id.equals(getValidUserID())) {
                    return code;
                }
            }
        }
    }
}
}
