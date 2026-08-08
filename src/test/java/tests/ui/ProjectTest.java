package tests.ui;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import tests.base.BaseTest;

public class ProjectTest extends BaseTest {
    public static final String projectName = "TMS01";
    public static final String projectCode = "TMS001";

    @Test(groups = "smoke", priority = 1)
    @Owner("Yanina Savich")
    @TmsLink("TC-002")
    @Feature("Projects")
    @Story("Create Project")
    @Description("Проверка создания нового проекта")
    public void checkCreateProject() {

        loginPage.openPage()
                .login(
                        user,
                        password)
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .shouldHaveProject(projectName)
                .openPage()
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }

    @Test(groups = "regression", priority = 2)
    @Owner("Yanina Savich")
    @TmsLink("TC-009")
    @Feature("Projects")
    @Story("Edit Project")
    @Description("Редактирование существующего проекта")
    public void checkEditProject() {
        loginPage.openPage()
                .login(
                        user,
                        password)
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .openPage()
                .shouldHaveProject(projectName);
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
        dashboardPage.shouldHaveProject(projectName)
                .openPage()
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }

    @Test(groups = "regression", priority = 3)
    @Owner("Yanina Savich")
    @TmsLink("TC-016")
    @Feature("Projects")
    @Story("Delete Project")
    @Description("Проверка удаления существующего проекта")
    public void checkDeleteProject() {
        loginPage.openPage()
                .login(
                        user,
                        password)
                .clickCreateProject()
                .setProjectName(projectName)
                .setProjectCode(projectCode)
                .clickCreateProject()
                .openPage()
                .shouldHaveProject(projectName)
                .deleteProject(projectName)
                .shouldNotHaveProject(projectName);
    }
}
