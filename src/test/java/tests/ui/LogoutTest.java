package tests.ui;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import tests.base.BaseTest;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static ui.pages.LoginPage.SIGN_IN;

public class LogoutTest extends BaseTest {
    @Test(groups = "smoke")
    @Owner("Yanina Savich")
    @TmsLink("TC-016")
    @Feature("Logout")
    @Story("Logout")
    @Description("Проверка выхода из системы")
    public void checkLogout() {
        loginPage.openPage();
        loginPage.login(
                user,
                password
        );
        webdriver().shouldHave(urlContaining("/projects"));
        loginPage.logout();
        webdriver().shouldHave(urlContaining("/login"));
        $(byText(SIGN_IN)).shouldBe(visible);
    }
}
