package adapters;

import com.google.gson.Gson;
import models.project.ProjectRq;
import models.project.ProjectRs;

import static io.restassured.RestAssured.given;

public class ProjectAdapter extends BaseAdapter{

    static Gson gson = new Gson();

    public static ProjectRs createProject(ProjectRq rq) {
        return given()
                .spec(spec)
                .body(gson.toJson(rq))
                .when()
                .post("/project")
                .then()
                .log().all()
                .spec(ok200)
                .extract()
                .as(ProjectRs.class);
    }

    public static void deleteProject(String code) {
        given()
                .spec(spec)
                .log().all()
                .when()
                .delete("/project/" + code)
                .then()
                .log().all()
                .spec(ok200);
    }
}
