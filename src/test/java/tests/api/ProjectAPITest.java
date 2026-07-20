package tests.api;

import api.models.project.ProjectRq;
import api.models.project.ProjectRs;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static api.adapters.ProjectAdapter.createProject;

public class ProjectAPITest extends BaseAPITest {

    private final String CODE = "QA";

    @Test(groups = "api")
    @Owner("Yanina Savich")
    @TmsLink("API-TC-001")
    @Feature("API")
    @Story("Create Project")
    @Description("Проверка создания нового проекта через API")
    public void checkCreateProject() {
        ProjectRq rq = ProjectRq.builder()
                .title("QA34")
                .code("QA")
                .description("test")
                .access("all")
                .group("test")
                .build();

        ProjectRs rs = createProject(rq);
        Assert.assertTrue(rs.status);
        Assert.assertEquals(rs.result.code, "QA");
    }
}
