package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static dict.Elements.SIGN_IN;
import static utils.PropertyReader.getProperty;

public class LogoutTest extends BaseTest {
    @Test(groups = "smoke")
    @TmsLink("TC-00")
    @Feature("Logout")
    @Story("Logout")
    @Description("Проверка выхода из системы")
    public void checkLogout() {
        loginPage.openPage();
        loginPage.login(
                getProperty("user"),
                getProperty("password")
        );
        webdriver().shouldHave(urlContaining("/projects"));
        loginPage.logout();
        webdriver().shouldHave(urlContaining("/login"));
        $(byText(SIGN_IN)).shouldBe(visible);
    }
}
