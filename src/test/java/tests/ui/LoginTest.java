package tests.ui;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import tests.base.BaseTest;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class LoginTest extends BaseTest {
    @Test(groups = "smoke")
    @Owner("Yanina Savich")
    @TmsLink("TC-001")
    @Feature("Authentication")
    @Story("Login")
    @Description("Проверка успешной авторизации пользователя")
    public void checkLoginWithPositiveCred() {
        loginPage.openPage();
        loginPage.login(user, password);
        webdriver().shouldHave(urlContaining("/projects"), Duration.ofSeconds(10));
        $(byText("Create new project")).shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test(groups = "regression")
    @Owner("Yanina Savich")
    @TmsLink("TC-1.2")
    @Feature("Authentication")
    @Story("Login")
    public void checkLoginWithEmptyPassword() {
        loginPage.openPage();
        loginPage.login(user, "");
        webdriver().shouldHave(urlContaining("/login"));
        loginPage.shouldHaveRequiredFieldError();
    }

    @Test(groups = "regression")
    @Owner("Yanina Savich")
    @TmsLink("TC-1.3")
    @Feature("Authentication")
    @Story("Login")
    public void checkLoginWithEmptyEmail() {
        loginPage.openPage();
        loginPage.login("", password);
        webdriver().shouldHave(urlContaining("/login"));
        loginPage.shouldHaveRequiredFieldError();
    }

    @Test(groups = "regression")
    @Owner("Yanina Savich")
    @TmsLink("TC-1.4")
    @Feature("Authentication")
    @Story("Login")
    public void checkLoginWithNegativeCred() {
        loginPage.openPage();
        loginPage.login("test@gmail.com", "password");
        webdriver().shouldHave(urlContaining("/reset"));
    }
}
