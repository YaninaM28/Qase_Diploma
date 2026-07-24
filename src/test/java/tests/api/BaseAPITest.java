package tests.api;

import api.adapters.ProjectAdapter;
import api.models.project.ProjectRq;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseAPITest {

    protected String projectCode;

    @BeforeMethod
    public void setUp() {
        projectCode = "QA" + (System.currentTimeMillis() % 1000000);
        String projectTitle = "Project " + projectCode;

        ProjectRq rq = ProjectRq.builder()
                .title(projectTitle)
                .code(projectCode)
                .description("test description")
                .access("all")
                .build();

        ProjectAdapter.createProject(rq);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        ProjectAdapter.deleteProject(projectCode);
    }
}
