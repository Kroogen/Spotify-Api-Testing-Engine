package com.mario.spotify.api;

import com.mario.spotify.utils.ConfigLoader;
import com.mario.spotify.utils.TokenManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

    public static RequestSpecification getRequestSpecification() {
        String baseUrl = ConfigLoader.getInstance().getBaseUrl();
        String access_token = TokenManager.getToken();

        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization", "Bearer " + access_token)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getFakeRequestSpecification(String fakeToken) {
        String baseUrl = ConfigLoader.getInstance().getBaseUrl();

        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization", "Bearer " + fakeToken)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
}
