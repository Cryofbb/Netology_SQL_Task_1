package ru.netology;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    @SneakyThrows
    public Connection getConnection() {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
    }

    @SneakyThrows
    public void clearDB() {
        var connection = getConnection();
        connection.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
        connection.createStatement().executeUpdate("TRUNCATE cards;");
        connection.createStatement().executeUpdate("TRUNCATE auth_codes;");
        connection.createStatement().executeUpdate("TRUNCATE users;");
        connection.createStatement().executeUpdate("TRUNCATE card_transactions");
        connection.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS = 1;");

    }

    @SneakyThrows
    public String getValidUserID() {
        var connection = getConnection();
        String id = "";
        try (var rs = connection.createStatement().executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                id = rs.getString("id");
                var login = rs.getString("login");
                if (login.equals("vasya")) {
                    break;
                }
            }
        }
        return id;
    }

    @SneakyThrows
    public String getCode() {
        var conn = getConnection();
        String code = "";
        try (var rs = conn.createStatement().executeQuery("SELECT * FROM auth_codes ORDER BY created DESC")) {
            while (rs.next()) {
                code = rs.getString("code");
                var user_id = rs.getString("user_id");
                if (user_id.equals(getValidUserID())) {
                    break;
                }
            }
        }
        return code;
    }
}

