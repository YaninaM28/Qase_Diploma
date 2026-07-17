package api.adapters;

import api.models.project.ProjectRq;
import api.models.project.ProjectRs;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class ProjectAdapter extends BaseAdapter{

    @Step("")
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

    @Step("")
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
