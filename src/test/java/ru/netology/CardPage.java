package ru.netology;


package ru.netology.login;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public CardPage() {
        heading.shouldBe(visible);
    }
}