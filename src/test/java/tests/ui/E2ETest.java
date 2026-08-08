package tests.ui;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import tests.base.BaseTest;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static tests.ui.ProjectTest.projectCode;
import static tests.ui.ProjectTest.projectName;
import static tests.ui.SuiteTest.suiteDescription;
import static tests.ui.SuiteTest.suiteName;
import static tests.ui.TestCaseTest.testCaseName;
import static ui.pages.LoginPage.CREATE_NEW_PROJECT;

public class E2ETest extends BaseTest {

    @Test(groups = "smoke")
    @Owner("Yanina Savich")
    @TmsLink("TC-008")
    @Feature("Full business test")
    @Story("End to end test")
    @Description("Проверка полного теста с входом, созданием проекта, созданием suite, test case с шагами, и затем удаление test case, suite и самого проекта")
    public void checkEnd2End() {
        loginPage.openPage();
        loginPage.login(
                        user,
                        password);
        webdriver().shouldHave(urlContaining("/projects"));
        $(byText(CREATE_NEW_PROJECT)).shouldBe(visible);
                dashboardPage.clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .shouldHaveProject(projectName)
                .clickProject(projectName)
                .openCreateSuiteForm()
                .createSuite(suiteName, suiteDescription)
                .shouldHaveSuite(suiteName);
        testCasePage.openCreateTestCaseForm()
                .setTestCaseName(testCaseName)
                .setTestCaseStep()
                .saveTestCase();
        testCasePage.shouldNotHaveTestCase(testCaseName)
                     .deleteTestCase(testCaseName);
        dashboardPage.openPage()
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }
}
