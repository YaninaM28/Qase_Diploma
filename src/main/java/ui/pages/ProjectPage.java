package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static ui.pages.LoginPage.CREATE_PROJECT;

@Log4j2
public class ProjectPage {

    private final String PROJECT_NAME = "#project-name";
    private final String PROJECT_CODE = "#project-code";
    private final String UPDATE_PROJECT = "Update settings";


    @Step("Ввести название проекта: {name}")
    public ProjectPage setProjectName(String name) {
        log.info("Setting project name '{}'", name);
        $(PROJECT_NAME).setValue(name);
        return this;
    }

    @Step("Ввести код проекта: {code}")
    public ProjectPage setProjectCode(String code) {
        log.info("Setting project code '{}'", code);
        $(PROJECT_CODE).setValue(code);
        return this;
    }

    @Step("Создать проект")
    public DashboardPage clickCreateProject() {
        log.info("Creating project");
        $(byText(CREATE_PROJECT)).click();
        return new DashboardPage();
    }

    @Step("Редактировать существующий проект")
    public ProjectPage clickUpdateProject() {
        log.info("Saving project changes");
        $(byText(UPDATE_PROJECT)).click();
        return this;
    }
}
