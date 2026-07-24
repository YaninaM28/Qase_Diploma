package tests.api;

import api.adapters.ProjectAdapter;
import api.models.project.ProjectRq;
import api.models.project.ProjectRs;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static api.adapters.ProjectAdapter.createProject;

public class ProjectAPITest extends BaseAPITest {

    @Test(groups = "api")
    @Owner("Yanina Savich")
    @TmsLink("API-TC-001")
    @Epic("API")
    @Feature("Project API")
    @Story("Create Project")
    @Description("Проверка создания нового проекта через API")
    public void checkCreateProject() {
        String newProjectCode = "QA" + System.currentTimeMillis() % 1000000;
        ProjectRq rq = ProjectRq.builder()
                .title("QA34")
                .code(newProjectCode)
                .description("test")
                .access("all")
                .build();

        ProjectRs rs = createProject(rq);
        try {
            Assert.assertTrue(rs.status);
            Assert.assertEquals(rs.result.code, newProjectCode);
        } finally {
            ProjectAdapter.deleteProject(newProjectCode);
        }
    }
}
