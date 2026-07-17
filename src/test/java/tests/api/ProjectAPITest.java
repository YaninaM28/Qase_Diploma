package tests.api;

import api.adapters.ProjectAdapter;
import api.models.project.ProjectRq;
import api.models.project.ProjectRs;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static api.adapters.ProjectAdapter.createProject;

public class ProjectAPITest {

    private final String CODE = "QA";

    @Test
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

    @AfterMethod
    public void deleteProject() {
        ProjectAdapter.deleteProject(CODE);
    }
}
