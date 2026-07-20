package ui.pages;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.shadowCss;
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
        log.info("Body:\n{}", $("body").getText());
        return this;
    }

    @Step("Авторизоваться своим юзером")
    public LoginPage login(String user, String password) {
        log.info("Opening Login page");
        acceptCookiesIfPresent();
//        $(shadowCss("#accept", "#usercentrics-cmp-ui")).click();
        $(LOGIN).setValue(user);
        $(PASSWORD).setValue(password);
        log.info("User = {}", user);
        log.info("Password empty = {}", password == null || password.isEmpty());
        $(byText(SIGN_IN)).click();
        log.info("User logged in");
        return this;
    }

//    $(byText(CREATE_NEW_PROJECT))
//            .shouldBe(visible, Duration.ofSeconds(30));

    private void acceptCookiesIfPresent() {
        try {
            var acceptButton = $(shadowCss("#accept", "#usercentrics-cmp-ui"));

            if (acceptButton.exists()) {
                log.info("Usercentrics cookie banner found. Accepting cookies...");
                acceptButton.shouldBe(visible).click();
            } else {
                log.info("Usercentrics cookie banner not found. Continue without accepting cookies.");
            }

        } catch (Exception e) {
            log.warn("Unable to accept cookies. Continue without it.", e);
        }
    }

    @Step("Выход из системы")
    public LoginPage logout() {
        log.info("Logging out");
        $(USER_AVATAR).click();
        $(byText(LOGOUT)).click();
        return this;
    }
}
