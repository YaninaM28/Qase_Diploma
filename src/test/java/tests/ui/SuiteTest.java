package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import tests.base.BaseTest;

import static tests.ui.ProjectTest.projectCode;
import static tests.ui.ProjectTest.projectName;
import static utils.PropertyReader.getProperty;

public class SuiteTest extends BaseTest {

    public static final String suiteName = "Suite_1";
    public static final String suiteDescription = "Test_Suite";

    @Test(groups = "smoke", priority = 1)
    @TmsLink("TC-004")
    @Feature("Suits")
    @Story("Create Suite")
    @Description("Проверка создания нового suite")
    public void checkCreateSuite() {
        loginPage.openPage()
                .login(
                        getProperty("user"),
                        getProperty("password"))
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .shouldHaveProject(projectName)
                .clickProject(projectName);
        suitePage.createSuite(suiteName, suiteDescription);
        dashboardPage.openPage()
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }

    @Test(groups = "smoke", priority = 2)
    @TmsLink("TC-009")
    @Feature("Suits")
    @Story("Edit Suite")
    @Description("Редактирование suite")
    public void checkEditeSuite() {
        loginPage.openPage()
                .login(
                        getProperty("user"),
                        getProperty("password"))
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .clickProject(projectName)
                .createSuite(suiteName, suiteDescription)
                .shouldHaveSuite(suiteName)
                .editSuite()
                .setSuiteName("Suite_edited")
                .setSuiteDescription("New")
                .saveEditedSuite()
                .shouldHaveSuite("Suite_edited")
                .editSuite()
                .setSuiteName(suiteName)
                .setSuiteDescription(suiteDescription)
                .saveEditedSuite()
                .shouldHaveSuite(suiteName);
        dashboardPage.openPage()
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }

    @Test(groups = "smoke", priority = 3)
    @TmsLink("TC-005")
    @Feature("Suits")
    @Story("Create Nested Suite")
    @Description("Проверка создания нового вложенного suite")
    public void checkCreateNestedSuite() {
        loginPage.openPage()
                .login(
                        getProperty("user"),
                        getProperty("password"))
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .clickProject(projectName)
                .createSuite(suiteName, suiteDescription)
                .shouldHaveSuite(suiteName)
                .selectSuite()
                .setSuiteName("nested suite_01")
                .setSuiteDescription("Nested")
                .submitSuite();
        dashboardPage.openPage()
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }

    @Test(groups = "regression", priority = 4, dependsOnMethods = "checkCreateSuite")
    @TmsLink("TC-014")
    @Feature("Suits")
    @Story("Delete Suite")
    @Description("Проверка удаления suite")
    public void checkDeleteSuite() {
        loginPage.openPage()
                .login(
                        getProperty("user"),
                        getProperty("password"))
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .clickProject(projectName)
                .createSuite(suiteName, suiteDescription)
                .shouldHaveSuite(suiteName)
                .selectSuite()
                .deleteSuite(suiteName)
                .shouldNotHaveSuite(suiteName);
        dashboardPage.openPage()
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }
}
