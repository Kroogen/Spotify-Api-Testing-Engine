package com.mario.spotify.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SearchApi {

    private final String SEARCH_PATH = "/search";

    public Response getTrackId(String trackName) {
        RequestSpecification requestSpecification = SpecBuilder.getRequestSpecification();
        ResponseSpecification responseSpecification = SpecBuilder.getResponseSpec();

        return RestAssured
                .given()
                .spec(requestSpecification)
                .queryParam("q", "track:" + trackName)
                .queryParam("type", "track")
                .when()
                .get(SEARCH_PATH)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }

}
