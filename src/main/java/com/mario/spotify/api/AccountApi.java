package com.mario.spotify.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AccountApi {

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