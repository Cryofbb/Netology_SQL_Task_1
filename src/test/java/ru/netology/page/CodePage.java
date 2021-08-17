package ru.netology.page;

import ru.netology.data.Database;

import static com.codeborne.selenide.Selenide.$;

public class CodePage {

    public CardPage validVerify(Database.UserCode verifyCode) {
        $("[data-test-id=code] input").setValue(verifyCode.getVerifyCode());
        $("[data-test-id=action-verify]").click();
        return new CardPage();
    }

    public void invalidVerify(Database.UserCode verifyCode){
        $("[data-test-id=code] input").setValue(verifyCode.getVerifyCode());
        $("[data-test-id=action-verify]").click();
    }
}
