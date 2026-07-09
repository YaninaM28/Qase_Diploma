package pages;

import dict.Elements;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class DashboardPage {
    private static final String ACTION_MENU = "button[aria-label='Open action menu']";
    private static final String REMOVE_BUTTON = "[data-testid='remove']";
    private static final String CONFIRM_DELETE_BUTTON = "//span[text()='Delete project']";
    private static final String EDIT_BUTTON = "[data-testid='settings']";

    @Step("Открыть Dashboard с проектами")
    public DashboardPage openPage() {
        open("/projects");
        return this;
    }

    @Step("Создать новый проект")
    public ProjectPage clickCreateProject() {
        $(byText(Elements.CREATE_NEW_PROJECT)).click();
        return new ProjectPage();
    }
    @Step("Проекта нет на главной странице")
    public DashboardPage shouldNotHaveProject(String projectName) {
        $(byText(projectName)).shouldNot(exist);
        return this;
    }

    @Step("Проект отображается на главной странице")
    public DashboardPage shouldHaveProject(String projectName) {
        $(byText(projectName)).shouldBe(visible);
        return this;
    }

    @Step("Открыть проект")
    public SuitePage clickProject(String projectName) {
        $(byText(projectName)).shouldBe(visible).click();
        return new SuitePage();
    }

    @Step("Редактировать проект")
    public ProjectPage editProject(String projectName) {
        $(byText(projectName))
                .ancestor("tr")
                .find(ACTION_MENU)
                .click();
        $(EDIT_BUTTON).click();
        log.info("Editing project {}", projectName);
        return new ProjectPage();
    }

    @Step("Удалить проект")
    public DashboardPage deleteProject(String projectName) {
        $(byText(projectName))
                .ancestor("tr")
                .find(ACTION_MENU)
                .click();
        $(REMOVE_BUTTON).click();
        $x(CONFIRM_DELETE_BUTTON).click();
        log.info("Deleting project {}", projectName);
        return this;
    }
}