package Spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;

public class LoginSpec {
    public static RequestSpecification loginRequestSpec = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .baseUri("https://reqres.in")
            .basePath("/api");

    public static ResponseSpecification loginResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .build();
}

