package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static ui.pages.LoginPage.CREATE_NEW_PROJECT;

@Log4j2
public class DashboardPage {
    private static final String ACTION_MENU = "button[aria-label='Open action menu']";
    private static final String REMOVE_BUTTON = "[data-testid='remove']";
    private static final String CONFIRM_DELETE_BUTTON = "//span[text()='Delete project']";
    private static final String EDIT_BUTTON = "[data-testid='settings']";

    @Step("Открыть Dashboard с проектами")
    public DashboardPage openPage() {
        log.info("Opening Dashboard page");
        open("/projects");
        return this;
    }

    @Step("Дождаться открытия Dashboard")
    public DashboardPage waitUntilOpened() {
        webdriver().shouldHave(urlContaining("/projects"), Duration.ofSeconds(20));
        return this;
    }

    @Step("Создать новый проект")
    public ProjectPage clickCreateProject() {
        log.info("Clicking 'Create new project'");
        $(byText(CREATE_NEW_PROJECT)).shouldBe(visible, Duration.ofSeconds(30)).click();
        return new ProjectPage();
    }

    @Step("Проекта нет на главной странице")
    public DashboardPage shouldNotHaveProject(String projectName) {
        log.info("Verifying project '{}' is displayed", projectName);
        $(byText(projectName)).shouldNot(exist);
        return this;
    }

    @Step("Проект отображается на главной странице")
    public DashboardPage shouldHaveProject(String projectName) {
        log.info("Verifying project '{}' is not displayed", projectName);
        $(byText(projectName)).shouldBe(visible, Duration.ofSeconds(15));
        return this;
    }

    @Step("Открыть проект")
    public SuitePage clickProject(String projectName) {
        log.info("Opening project '{}'", projectName);
        $(byText(projectName)).shouldBe(visible).click();
        return new SuitePage();
    }

    @Step("Редактировать проект")
    public ProjectPage editProject(String projectName) {
        log.info("Editing project {}", projectName);
        $(byText(projectName))
                .ancestor("tr")
                .find(ACTION_MENU)
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();
        $(EDIT_BUTTON).shouldBe(visible, Duration.ofSeconds(10)).click();
        return new ProjectPage();
    }

    @Step("Удалить проект")
    public DashboardPage deleteProject(String projectName) {
        log.info("Deleting project '{}'", projectName);
        $(byText(projectName))
                .ancestor("tr")
                .find(ACTION_MENU)
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();
        $(REMOVE_BUTTON).shouldBe(visible, Duration.ofSeconds(10)).click();
        $x(CONFIRM_DELETE_BUTTON).shouldBe(visible, Duration.ofSeconds(10)).click();
        $x(CONFIRM_DELETE_BUTTON).shouldNotBe(visible, Duration.ofSeconds(10));
        return this;
    }
}