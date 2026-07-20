package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class TestCasePage {

    private final String CREATE_NEW_TEST_CASE = "Create test case";
    private final String TEST_CASE_TITLE = "//input[@name='title']";
    private final String DESCRIPTION = "div[contenteditable='true']";
    private final String SAVE_TEST_CASE_BUTTON = "Save";
    private final String CANCEL_TEST_CASE_BUTTON = "Cancel";
//    private final String CONFIRM_DELETE_TEST_CASE = "//div[text()='Delete the test case']";
    private final String DELETE_TEST_CASE = "//span[text()='Delete']";
    private final String ADD_TEST_CASE_STEP = "//span[text()='New step']";
    private final String STEP_ACTION = "div[data-placeholder='Step action']";
    private final String STEP_DATA = "div[data-placeholder='Data']";
    private final String STEP_RESULT = "div[data-placeholder='Expected result']";

    @Step("Открыть форму создания test case")
    public TestCasePage openCreateTestCaseForm() {
        log.info("Opening test case creation form");
        $("svg[data-icon='ellipsis-vertical']").click();
        $(byText(CREATE_NEW_TEST_CASE)).click();
        return this;
    }

    @Step("Очистить и ввести значение в input")
    public TestCasePage clearAndType(SelenideElement element, String value) {
        executeJavaScript("arguments[0].value = '';", element);
        executeJavaScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
        element.setValue(value);
        return this;
    }

    @Step("Ввести название test case: {testCaseName}")
    public TestCasePage setTestCaseName(String testCaseName) {
        log.info("Setting test case name '{}'", testCaseName);
        $x(TEST_CASE_TITLE).shouldBe(visible).setValue(testCaseName);
        return this;
    }

    @Step("Ввести описание для test case: {description}")
    public TestCasePage setTestCaseDescription(String description) {
        log.info("Setting test case description");
        $(DESCRIPTION).click();
        $(DESCRIPTION).sendKeys(description);
        return this;
    }

    @Step("Добавить шаг для test case")
    public TestCasePage setTestCaseStep() {
        log.info("Adding test step");
        SelenideElement editor = $("div[contenteditable='true']");
        executeJavaScript("arguments[0].scrollIntoView(true);", editor);
        $x(ADD_TEST_CASE_STEP).shouldBe(visible).click();
        $(STEP_ACTION).shouldBe(visible).click();
        $(STEP_ACTION).sendKeys("Step_1");
        $(STEP_DATA).click();
        $(STEP_DATA).sendKeys("Data_1");
        $(STEP_RESULT).click();
        $(STEP_RESULT).sendKeys("Result_1");
        return this;
    }

    @Step("Подтвердить создание test case")
    public SuitePage saveTestCase() {
        log.info("Saving test case");
        $(byText(SAVE_TEST_CASE_BUTTON)).click();
        return new SuitePage();
    }

    @Step("Отменить создание test case")
    public TestCasePage cancelTestCase() {
        log.info("Cancelling test case creation");
        $(byText(CANCEL_TEST_CASE_BUTTON)).click();
        return this;
    }

    @Step("Полный сценарий создания test case")
    public TestCasePage createTestCase(String testCaseName, String description) {
        openCreateTestCaseForm();
        setTestCaseName(testCaseName);
        setTestCaseDescription(description);
        saveTestCase();
        return this;
    }

    @Step("Test case отображается выбранном suite")
    public TestCasePage shouldHaveTestCase(String testCaseName) {
        log.info("Verifying test case '{}' is displayed", testCaseName);
        $(byText(testCaseName)).shouldBe(visible);
        return this;
    }

    @Step("Test case не отображается в suite")
    public TestCasePage shouldNotHaveTestCase(String testCaseName) {
        log.info("Verifying test case '{}' is not displayed", testCaseName);
        $(byText(testCaseName)).shouldNot(exist);
        return this;
    }

    @Step("Удалить test case")
    public SuitePage deleteTestCase(String testCaseName) {
        log.info("Deleting test case '{}'", testCaseName);
        $("svg[data-icon='trash']").click();
        $x(DELETE_TEST_CASE).click();
        return new SuitePage();
    }
}
