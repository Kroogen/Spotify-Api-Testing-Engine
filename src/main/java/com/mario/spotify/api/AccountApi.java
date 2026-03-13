package com.mario.spotify.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AccountApi {

    @Step("Getting the User Profile")
    public static Response getUserProfile() {
        return given()
                .spec(SpecBuilder.getRequestSpecification())
                .when()
                .get("/me")
                .then()
                .extract()
                .response();
    }
}