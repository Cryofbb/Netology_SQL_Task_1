package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.Database;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class LoginTest {
    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    @AfterAll
    public void cleanBD() {
        Database.cleanBD();
    }

    @Test
    @Order(1)
    @DisplayName("All valid")
    public void validLogin() {
        var codePage = new LoginPage().validLogin(DataHelper.getValidInfo());
        codePage.validVerify(Database.getCode());
        $("[data-test-id=dashboard]").shouldBe(visible);
    }

    @Test
    @Order(2)
    @DisplayName("Error with invalid user data")
    public void invalidLogin() {
        new LoginPage().validLogin(DataHelper.getInvalidInfo());
        $("[data-test-id=error-notification]").shouldBe(visible);
    }

    @Test
    @Order(3)
    @DisplayName("Error with invalid verify code")
    public void invalidCode() {
        var codePage = new LoginPage().validLogin(DataHelper.getValidInfo());
        codePage.invalidVerify(Database.getInvalidCode());
        $("[data-test-id=error-notification] .notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }

    @Test
    @Order(4)
    @DisplayName("Block with 3 invalid password")
    public void invalidPass3Times() {
        var loginPage = new LoginPage();
        loginPage.validLogin(DataHelper.getInvalidPass());
        open("http://localhost:9999");
        loginPage.validLogin(DataHelper.getInvalidPass());
        open("http://localhost:9999");
        loginPage.validLogin(DataHelper.getInvalidPass());
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(exactText("Система заблокирована! Попробуйте позднее"));
    }

    @Test
    @Order(5)
    @DisplayName("Block with 3 invalid verify code")
    public void invalidCode3Times() {
        var codePage = new LoginPage();
        codePage.validLogin(DataHelper.getValidInfo()).invalidVerify(Database.getInvalidCode());
        open("http://localhost:9999");
        codePage.validLogin(DataHelper.getValidInfo()).invalidVerify(Database.getInvalidCode());
        open("http://localhost:9999");
        codePage.validLogin(DataHelper.getValidInfo()).invalidVerify(Database.getInvalidCode());
        $("[data-test-id=error-notification] .notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! Превышено количество попыток ввода кода!"));
    }
}
