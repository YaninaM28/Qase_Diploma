package ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class LoginPage {

    public static final String SIGN_IN = "Sign in";
    public static final String CREATE_NEW_PROJECT = "Create new project";
    protected static final String CREATE_PROJECT = "Create project";
    private final String LOGIN = "[name=email]";
    private final String PASSWORD = "[name=password]";
    private final String USER_AVATAR = "img[aria-label='User avatar']";
    private final String LOGOUT = "Sign out";

    @Step("Открыть страницу логина")
    public LoginPage openPage() {
        open("/login");
        log.info("URL: {}", WebDriverRunner.url());
        log.info("Title: {}", title());
        acceptCookiesIfPresent();
        sleep(500);
        return this;
    }

    @Step("Авторизоваться своим юзером")
    public LoginPage login(String user, String password) {
        log.info("Opening Login page");
        acceptCookiesIfPresent();
        $(LOGIN).shouldBe(visible, Duration.ofSeconds(10)).sendKeys(user);
        $(PASSWORD).shouldBe(visible, Duration.ofSeconds(10)).sendKeys(password);
        $(PASSWORD).shouldHave(value(password));
        log.info("User = {}", user);
        log.info("Password empty = {}", password == null || password.isEmpty());
        SelenideElement signInButton = $(byText(SIGN_IN))
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(enabled, Duration.ofSeconds(15));
        signInButton.click();
        log.info("User logged in");
        return this;
    }

    private void acceptCookiesIfPresent() {
//        try {
//            SelenideElement acceptButton = $(shadowCss("#accept", "#usercentrics-cmp-ui"));
//
//            if (!acceptButton.exists()) {
//                acceptButton = $(byText("Accept all"));
//            }
//            if (acceptButton.exists()) {
//                acceptButton.shouldBe(visible, Duration.ofSeconds(5)).click();
//                log.info("Cookie banner closed");
//            }
//
//        } catch (Exception e) {
//            log.warn("Unable to accept cookies. Continue without it.", e);
//        }
        executeJavaScript (
                        "let uc = document.getElementById('usercentrics-cmp-ui');" +
                                "if (uc) uc.remove();"
        );

    }

    @Step("Выход из системы")
    public LoginPage logout() {
        log.info("Logging out");
        $(USER_AVATAR).click();
        $(byText(LOGOUT)).click();
        return this;
    }
}
