package api.adapters;

import io.qameta.allure.Step;
import api.models.cases.CaseRq;
import api.models.cases.CaseRs;

import static api.adapters.BaseAdapter.*;
import static io.restassured.RestAssured.given;

public class CaseAdapter {

    @Step("")
    public static CaseRs createCase(String projectCode, CaseRq rq) {
        return given()
                .spec(spec)
                .body(gson.toJson(rq))
                .when()
                .post("/case/" + projectCode)
                .then()
                .log().all()
                .spec(ok200)
                .extract()
                .as(CaseRs.class);
    }

    @Step("")
    public static CaseRs updateCase(String projectCode, int caseId, CaseRq rq) {
        return given()
                .spec(spec)
                .body(gson.toJson(rq))
                .log().all()
                .when()
                .patch("/case/" + projectCode + "/" + caseId)
                .then()
                .log().all()
                .spec(ok200)
                .extract()
                .as(CaseRs.class);
    }

    @Step("")
    public static void deleteCase(String projectCode, int caseId) {
        given()
                .spec(spec)
                .log().all()
                .when()
                .delete("/case/" + projectCode + "/" + caseId)
                .then()
                .log().all()
                .spec(ok200);
    }

    @Step("")
    public static CaseRs getCase(String projectCode, int caseId) {
        return given()
                .spec(spec)
                .log().all()
                .when()
                .get("/case/" + projectCode + "/" + caseId)
                .then()
                .spec(ok200)
                .extract()
                .as(CaseRs.class);
    }
}
