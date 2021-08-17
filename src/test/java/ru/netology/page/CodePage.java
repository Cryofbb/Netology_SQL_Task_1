package ru.netology.page;

import ru.netology.data.Database;

import static com.codeborne.selenide.Selenide.$;

public class CodePage {

    public CardPage validVerify(Database.UserCode code) {
        $("[data-test-id=code] input").setValue(code.getVerifyCode());
        $("[data-test-id=action-verify]").click();
        return new CardPage();
    }
}
