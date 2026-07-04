package tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

import static utils.PropertyReader.getProperty;

public class ProjectTest extends BaseTest {
    public static final String projectName = "TMS01";
    public static final String projectCode = "TMS001";

    @Test(groups = "smoke", priority = 1)
    @TmsLink("TC-002")
    @Feature("Projects")
    @Story("Create Project")
    @Description("Проверка создания нового проекта")
    public void checkCreateProject() {

        loginPage.openPage()
                .login(
                        getProperty("user"),
                        getProperty("password"))
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .shouldHaveProject(projectName);
    }

    @Test(groups = "regression", priority = 2)
    @TmsLink("TC-008")
    @Feature("Projects")
    @Story("Edit Project")
    @Description("Редактирование существующего проекта")
    public void checkEditProject() {
        loginPage.openPage()
                .login(
                        getProperty("user"),
                        getProperty("password"));
        dashboardPage.editProject(projectName)
                .setProjectName("TMS01(edited)")
                .setProjectCode("TMS001new")
                .clickUpdateProject();
        dashboardPage.shouldHaveProject("TMS01(edited)");
        dashboardPage.openPage();
        dashboardPage.editProject("TMS01(edited)")
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickUpdateProject();
        dashboardPage.shouldHaveProject(projectName);
    }

    @Test(groups = "regression", priority = 3, dependsOnMethods = "checkCreateProject")
    @TmsLink("TC-015")
    @Feature("Projects")
    @Story("Delete Project")
    @Description("Проверка удаления существующего проекта")
    public void checkDeleteProject() {
        loginPage.openPage()
                .login(
                        getProperty("user"),
                        getProperty("password"))
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }
}
