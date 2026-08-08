package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
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
        log.info("Opening suite creation form");
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
        log.info("Setting suite name '{}'", suiteName);
        clearAndType($(SUITE_NAME), suiteName);
        return this;
    }

    @Step("Ввести описание для suite: {description}")
    public SuitePage setSuiteDescription(String description) {
        log.info("Setting suite description");
        $(DESCRIPTION).setValue(description);
        return this;
    }

    @Step("Подтвердить создание suite")
    public SuitePage submitSuite() {
        log.info("Creating suite");
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
    public SuitePage shouldHaveSuite(String suiteName) {
        log.info("Verifying suite '{}' is displayed", suiteName);
        $(byText(suiteName)).shouldBe(visible);
        return this;
    }

    @Step("Suite не отображается в проекте")
    public SuitePage shouldNotHaveSuite(String suiteName) {
        log.info("Verifying suite '{}' is not displayed", suiteName);
        $(byText(suiteName)).shouldNot(exist);
        return this;
    }

    @Step("Выбрать созданный suite")
    public SuitePage selectSuite() {
        $("svg[data-icon='ellipsis-vertical']").click();
        $(byText("Create suite")).click();
        return this;
    }

    @Step("Нажать Edit suite")
    public SuitePage editSuite() {
        log.info("Opening suite edit form");
        $("svg[data-icon='ellipsis-vertical']").click();
        $(byText("Edit")).click();
        return this;
    }

    @Step("Применить изменения для suite")
    public SuitePage saveEditedSuite() {
        log.info("Saving suite changes");
        $(byText(SAVE_SUITE_BUTTON)).click();
        return this;
    }

    @Step("Удалить suite")
    public SuitePage deleteSuite(String suiteName) {
        log.info("Deleting suite '{}'", suiteName);
        $("svg[data-icon='ellipsis-vertical']").click();
        $(byText("Delete")).click();
        $x(CONFIRM_DELETE_SUITE).click();
        $x(DELETE_SUITE).click();
        return this;
    }
}
