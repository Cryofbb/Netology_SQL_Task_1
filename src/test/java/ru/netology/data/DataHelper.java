package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }


    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getValidInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidInfo() {
        Faker faker = new Faker();
        String login = faker.pokemon().name();
        String pass = faker.internet().password();
        return new AuthInfo(login, pass);
    }
    public static AuthInfo getInvalidPass() {
        Faker faker = new Faker();
        String login = "vasya";
        String pass = faker.internet().password();
        return new AuthInfo(login, pass);
    }
}