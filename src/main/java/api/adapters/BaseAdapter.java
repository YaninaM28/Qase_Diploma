package api.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.PropertyReader;

public class BaseAdapter {

    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    private static String getToken() {

        String token = System.getProperty("token");

        if (token == null || token.isEmpty()) {
            token = PropertyReader.getProperty("token");
        }
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException(
                    "Token is missing. Add -Dtoken or token in config.properties"
            );
        }
        return token;
    }

    public static RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("https://api.qase.io")
            .setBasePath("/v1")
            .setContentType(ContentType.JSON)
            .addHeader("Token", getToken())
            .addFilter(new AllureRestAssured())
            .build();

    public static ResponseSpecification ok200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}
