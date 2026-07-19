package tests.ui;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import tests.base.BaseTest;

import static tests.ui.ProjectTest.projectCode;
import static tests.ui.ProjectTest.projectName;
import static tests.ui.SuiteTest.suiteDescription;
import static tests.ui.SuiteTest.suiteName;
import static tests.ui.TestCaseTest.testCaseName;

public class E2ETest extends BaseTest {

    @Test(groups = "smoke")
    @Owner("Yanina Savich")
    @TmsLink("TC-016")
    @Feature("Full business test")
    @Story("End to end test")
    @Description("Проверка полного теста с входом, созданием проекта, созданием suite, test case с шагами, и затем удаление test case, suite и самого проекта")
    public void checkEnd2End() {
        loginPage.openPage()
                .login(
                        user,
                        password)
                .clickCreateProject()
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
