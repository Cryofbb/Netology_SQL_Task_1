package ru.netology;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {
    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    @AfterAll
    public void cleanBD(){
    new Database().clearDB();
    }

    @Test
    @SneakyThrows
    @DisplayName("All valid")
    public void validLogin() {
        var database = new Database();
        var codePage = new LoginPage().validLogin(DataHelper.getValidInfo());
        codePage.validVerify(database.getCode());
        $("[data-test-id=dashboard]").shouldBe(visible);
    }

    @Test
    @SneakyThrows
    @DisplayName("Error with invalid user data")
    public void invalidLogin() {
        new LoginPage().validLogin(DataHelper.getInvalidInfo());
        $("[data-test-id=error-notification]").shouldBe(visible);
    }

    @Test
    @SneakyThrows
    @DisplayName("Error with invalid verify code")
    public void invalidCode() {
        var database = new Database();
        var codePage = new LoginPage().validLogin(DataHelper.getValidInfo());
        $("[data-test-id=code] .input__control").setValue("12345");
        $("[data-test-id=action-verify]").click();
        $("[data-test-id=error-notification] .notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }

    @Test
    @SneakyThrows
    @DisplayName("Block with 3 invalid password")
    public void invalidPass3Times() {
        Faker faker = new Faker();
        $("[data-test-id=login] .input__control").setValue("vasya");
        $("[data-test-id=password] .input__control").setValue(faker.internet().password());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password] .input__control").sendKeys(Keys.chord(Keys.CONTROL + "a", Keys.DELETE));
        $("[data-test-id=password] .input__control").setValue(faker.internet().password());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password] .input__control").sendKeys(Keys.chord(Keys.CONTROL + "a", Keys.DELETE));
        $("[data-test-id=password] .input__control").setValue(faker.internet().password());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(exactText("Система заблокирована! Попробуйте позднее"));
    }

    @Test
    @SneakyThrows
    @DisplayName("Block with 3 invalid verify code")
    public void invalidCode3Times() {
        var database = new Database();
        var codePage = new LoginPage().validLogin(DataHelper.getValidInfo());
        $("[data-test-id=code] .input__control").setValue("12345");
        $("[data-test-id=action-verify]").click();
        open("http://localhost:9999");
        var codePage2 = new LoginPage().validLogin(DataHelper.getValidInfo());
        $("[data-test-id=code] .input__control").setValue("12345");
        $("[data-test-id=action-verify]").click();
        open("http://localhost:9999");
        var codePage3 = new LoginPage().validLogin(DataHelper.getValidInfo());
        $("[data-test-id=code] .input__control").setValue("12345");
        $("[data-test-id=action-verify]").click();
        $("[data-test-id=error-notification] .notification__content").shouldBe(visible).shouldHave(exactText("Ошибка! Превышено количество попыток ввода кода!"));
    }
}
