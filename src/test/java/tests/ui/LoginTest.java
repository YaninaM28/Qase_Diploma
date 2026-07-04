package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static dict.Elements.CREATE_NEW_PROJECT;
import static utils.PropertyReader.getProperty;

public class LoginTest extends BaseTest {
    @Test(groups = "smoke")
    @TmsLink("TC-001")
    @Feature("Authentication")
    @Story("Login")
    @Description("Проверка успешной авторизации пользователя")
    public void checkLoginWithPositiveCred() {
        loginPage.openPage();
        loginPage.login(
                getProperty("user"),
                getProperty("password")
        );
        webdriver().shouldHave(urlContaining("/projects"));
        $(byText(CREATE_NEW_PROJECT)).shouldBe(visible);
    }

    @Test(groups = "regression")
    public void checkLoginWithEmptyPassword() {
        loginPage.openPage();
        loginPage.login(getProperty("user"),"");
        webdriver().shouldHave(urlContaining("/login"));
        $(byText("This field is required")).shouldBe(visible);
    }

    @Test(groups = "regression")
    public void checkLoginWithEmptyEmail() {
        loginPage.openPage();
        loginPage.login("", getProperty("password"));
        webdriver().shouldHave(urlContaining("/login"));
        $(byText("This field is required")).shouldBe(visible);
    }

    @Test(groups = "regression")
    public void checkLoginWithNegativeCred() {
        loginPage.openPage();
        loginPage.login("test@gmail.com","password");
        sleep(2000);
        webdriver().shouldHave(urlContaining("/reset"));
        $(byText("Send password reset link")).shouldBe(visible);
    }
}
