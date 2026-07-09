package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import tests.base.BaseTest;

import static tests.ui.ProjectTest.projectCode;
import static tests.ui.ProjectTest.projectName;
import static tests.ui.SuiteTest.suiteDescription;
import static tests.ui.SuiteTest.suiteName;
import static utils.PropertyReader.getProperty;

public class TestCaseTest extends BaseTest {

    public static final String testCaseName = "Test case_1";
    public static final String testCaseDescription = "Test case_desc";

    @Test(groups = "smoke", priority = 1)
    @TmsLink("TC-006")
    @Feature("Test case")
    @Story("Create test case")
    @Description("Проверка создания нового test case")
    public void checkCreateTestCase() {
        loginPage.openPage()
                .login(
                        getProperty("user"),
                        getProperty("password"))
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .shouldHaveProject(projectName)
                .clickProject(projectName)
                .createSuite(suiteName, suiteDescription)
                .shouldHaveSuite(suiteName);
        testCasePage.createTestCase(testCaseName, testCaseDescription)
                .shouldHaveTestCase(testCaseName);
        dashboardPage.openPage()
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }

    @Test(groups = "smoke", priority = 2)
    @TmsLink("TC-007")
    @Feature("Test case")
    @Story("Add test case with steps")
    @Description("Редактирование test case")
    public void checkAddTestCaseWithSteps() {
        loginPage.openPage()
                .login(
                        getProperty("user"),
                        getProperty("password"))
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .shouldHaveProject(projectName)
                .clickProject(projectName)
                .createSuite(suiteName, suiteDescription)
                .shouldHaveSuite(suiteName);
        testCasePage.openCreateTestCaseForm()
                .setTestCaseName("TestCase_with_steps")
                .setTestCaseStep()
                .saveTestCase();
        testCasePage.shouldHaveTestCase("TestCase_with_steps");
        dashboardPage.openPage()
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }

    @Test(groups = "regression", priority = 3)
    @TmsLink("TC-00")
    @Feature("Test case")
    @Story("Cancel test case")
    @Description("Проверка нажатия отмена при создании нового test case")
    public void checkCancelTestCase() {
        loginPage.openPage()
                .login(
                        getProperty("user"),
                        getProperty("password"))
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .shouldHaveProject(projectName)
                .clickProject(projectName)
                .createSuite(suiteName, suiteDescription)
                .shouldHaveSuite(suiteName);
        testCasePage.openCreateTestCaseForm()
                .setTestCaseName(testCaseName)
                .cancelTestCase()
                .shouldNotHaveTestCase(testCaseName);
        dashboardPage.openPage()
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }
    
    @Test(groups = "regression", priority = 4)
    @TmsLink("TC-013")
    @Feature("Test case")
    @Story("Delete test case")
    @Description("Проверка удаления test case")
    public void checkDeleteTestCase() {
        loginPage.openPage()
                .login(
                        getProperty("user"),
                        getProperty("password"))
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .shouldHaveProject(projectName)
                .clickProject(projectName)
                .createSuite(suiteName, suiteDescription)
                .shouldHaveSuite(suiteName);
        testCasePage.createTestCase(testCaseName, testCaseDescription)
                .shouldHaveTestCase(testCaseName)
                .deleteTestCase(testCaseName);
        testCasePage.shouldNotHaveTestCase(testCaseName);
        dashboardPage.openPage()
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }

}
