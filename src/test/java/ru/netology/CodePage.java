package ru.netology;

import static com.codeborne.selenide.Selenide.$;

public class CodePage {

    public CardPage validVerify(String verificationCode) {
        $("[data-test-id=code] input").setValue(verificationCode);
        $("[data-test-id=action-verify]").click();
        return new CardPage();
    }
}
