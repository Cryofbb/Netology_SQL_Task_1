package ru.netology;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public CodePage validLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=login] .input__control").setValue(info.getLogin());
        $("[data-test-id=password] .input__control").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        return new CodePage();
    }
}