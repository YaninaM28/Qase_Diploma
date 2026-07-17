package tests.api;

import api.adapters.ProjectAdapter;
import api.models.project.ProjectRq;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseAPITest {

    protected String projectCode;

    @BeforeMethod
    public void setUp() {

        projectCode = "QA" + (System.currentTimeMillis() % 100000);

        ProjectRq rq = ProjectRq.builder()
                .title("QA34")
                .code(projectCode)
                .description("test")
                .access("all")
                .group("test")
                .build();

        ProjectAdapter.createProject(rq);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        ProjectAdapter.deleteProject(projectCode);
    }
}
