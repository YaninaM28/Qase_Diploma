package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static dict.Elements.CREATE_PROJECT;

public class ProjectPage {
    private final String PROJECT_NAME = "#project-name";
    private final String PROJECT_CODE = "#project-code";
    private final String UPDATE_PROJECT = "Update settings";


    @Step("Ввести название проекта: {name}")
    public ProjectPage setProjectName(String name) {
        $(PROJECT_NAME).setValue(name);
        return this;
    }

    @Step("Ввести код проекта: {code}")
    public ProjectPage setProjectCode(String code) {
        $(PROJECT_CODE).setValue(code);
        return this;
    }

    @Step("Создать проект")
    public DashboardPage clickCreateProject() {
        $(byText(CREATE_PROJECT)).click();
        return new DashboardPage();
    }

    @Step("Редактировать существующий проект")
    public ProjectPage clickUpdateProject() {
        $(byText(UPDATE_PROJECT)).click();
        return this;
    }

}
