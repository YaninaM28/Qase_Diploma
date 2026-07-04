package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.shadowCss;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static dict.Elements.SIGN_IN;

@Log4j2
public class LoginPage {

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
        $(shadowCss("#accept", "#usercentrics-cmp-ui")).click();
        $(LOGIN).setValue(user);
        $(PASSWORD).setValue(password);
        $(byText(SIGN_IN)).click();
        log.info("User logged in");
        return new DashboardPage();
    }

    @Step("Выход из системы")
    public LoginPage logout() {
        $(USER_AVATAR).click();
        $(byText(LOGOUT)).click();
        return this;
    }

}
