package ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Log4j2
public class LoginPage {

    public static final String SIGN_IN = "Sign in";
    public static final String CREATE_NEW_PROJECT = "Create new project";
    protected static final String CREATE_PROJECT = "Create project";
    private final String LOGIN = "[name=email]";
    private final String PASSWORD = "[name=password]";
    private final String USER_AVATAR = "img[aria-label='User avatar']";
    private final String LOGOUT = "Sign out";
    private final String SIGN_IN_BUTTON = "button[type='submit']";
    private static final String REQUIRED_FIELD = "This field is required";

    @Step("Открыть страницу логина")
    public LoginPage openPage() {
        open("/login");
        log.info("URL: {}", WebDriverRunner.url());
        acceptCookiesIfPresent();
        return this;
    }

    @Step("Авторизоваться своим юзером")
    public LoginPage login(String user, String password) {
        log.info("Logging in as {}", user);
        $(LOGIN).shouldBe(visible, Duration.ofSeconds(10)).setValue(user);
        $(PASSWORD).shouldBe(visible, Duration.ofSeconds(10)).setValue(password);
        $(SIGN_IN_BUTTON).shouldBe(enabled, Duration.ofSeconds(10)).click();
        return this;
    }

    private void acceptCookiesIfPresent() {
        try {
            SelenideElement acceptButton = $(shadowCss("#accept", "#usercentrics-cmp-ui"));
            acceptButton.shouldBe(visible, Duration.ofSeconds(10)).click();
            log.info("Cookie banner closed");
        } catch (Throwable e) {
            log.info("Cookie banner not displayed");
        }
    }

    @Step("Проверить сообщение об обязательном поле")
    public LoginPage shouldHaveRequiredFieldError() {
        $(byText(REQUIRED_FIELD)).shouldBe(visible);
        return this;
    }

    @Step("Выход из системы")
    public LoginPage logout() {
        log.info("Logging out");
        $(USER_AVATAR).click();
        $(byText(LOGOUT)).click();
        return this;
    }
}
