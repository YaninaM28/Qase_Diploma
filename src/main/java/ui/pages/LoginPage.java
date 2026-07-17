package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.shadowCss;
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

    @Step("Открыть страницу логина")
    public LoginPage openPage() {
        open("/login");
        return this;
    }

    @Step("Авторизоваться своим юзером")
    public DashboardPage login(String user, String password) {
        log.info("Opening Login page");
        $(shadowCss("#accept", "#usercentrics-cmp-ui")).click();
        $(LOGIN).setValue(user);
        $(PASSWORD).setValue(password);
        $(byText(SIGN_IN)).click();
        log.info("User logged in");
        return new DashboardPage();
    }

    @Step("Выход из системы")
    public LoginPage logout() {
        log.info("Logging out");
        $(USER_AVATAR).click();
        $(byText(LOGOUT)).click();
        return this;
    }
}
