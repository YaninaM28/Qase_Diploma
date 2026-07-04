package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SuitePage {

    private final String CREATE_NEW_SUITE = "Create new suite";
    private final String SUITE_NAME = "#title";
    private final String DESCRIPTION = "#description";
    private final String SUBMIT_CREATE = "Create";
    private final String CONFIRM_DELETE_SUITE = "//div[text()='Delete the suite and all its test cases']";
    private final String DELETE_SUITE = "//span[text()='Delete']";
    private final String SAVE_SUITE_BUTTON = "Save";


    @Step("Открыть форму создания suite")
    public SuitePage openCreateSuiteForm() {
        $(byText(CREATE_NEW_SUITE)).click();
        return this;
    }

    @Step("Очистить и ввести значение в input")
    public SuitePage clearAndType(SelenideElement element, String value) {
        executeJavaScript("arguments[0].value = '';", element);
        executeJavaScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
        element.setValue(value);
        return this;
    }

    @Step("Ввести название suite: {suiteName}")
    public SuitePage setSuiteName(String suiteName) {
        clearAndType($(SUITE_NAME), suiteName);
        return this;
    }

    @Step("Ввести описание для suite: {description}")
    public SuitePage setSuiteDescription(String description) {
        $(DESCRIPTION).setValue(description);
        return this;
    }

    @Step("Подтвердить создание suite")
    public SuitePage submitSuite() {
        $(byText(SUBMIT_CREATE)).click();
        return this;
    }

    @Step("Полный сценарий создания suite")
    public SuitePage createSuite(String suiteName, String description) {
        openCreateSuiteForm();
        setSuiteName(suiteName);
        setSuiteDescription(description);
        submitSuite();
        return this;
    }
    @Step("Suite отображается в проекте")
    public ProjectPage shouldHaveSuite(String suiteName) {
        $(byText(suiteName)).shouldBe(visible);
        return new ProjectPage();
    }
    @Step("Suite не отображается в проекте")
    public ProjectPage shouldNotHaveSuite(String suiteName) {
        $(byText(suiteName)).shouldNot(exist);
        return new ProjectPage();
    }

    @Step("Выбрать созданный suite")
    public SuitePage selectSuite() {
        $("svg[data-icon='ellipsis-vertical']").click();
        $(byText("Create suite")).click();
        return this;
    }

    @Step("Нажать Edit suite")
    public SuitePage editSuite() {
        $("svg[data-icon='ellipsis-vertical']").click();
        $(byText("Edit")).click();
        return this;
    }

    @Step("Применить изменения для suite")
    public SuitePage saveEditedSuite() {
        $(byText(SAVE_SUITE_BUTTON)).click();
        return this;
    }

    @Step("Удалить suite")
    public SuitePage deleteSuite(String suiteName) {
        $("svg[data-icon='ellipsis-vertical']").click();
        $(byText("Delete")).click();
        $x(CONFIRM_DELETE_SUITE).click();
        $x(DELETE_SUITE).click();
        return this;
    }

}
