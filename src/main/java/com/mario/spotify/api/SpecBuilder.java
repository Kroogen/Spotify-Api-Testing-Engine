package com.mario.spotify.api;

import com.mario.spotify.utils.ConfigLoader;
import com.mario.spotify.utils.TokenManager;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpecBuilder {
    private static final Logger logger = LogManager.getLogger(SpecBuilder.class);

    @Step("Generating Request Specification with base URL and OAuth Token")
    public static RequestSpecification getRequestSpecification() {
        logger.info("Building Request Specification for a new API call");
        String baseUrl = ConfigLoader.getInstance().getBaseUrl();
        String access_token = TokenManager.getToken();

        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addFilter(new io.qameta.allure.restassured.AllureRestAssured())
                .addHeader("Authorization", "Bearer " + access_token)
                .log(LogDetail.ALL)
                .build();
    }

    @Step("Trying to generate a Request Specification with base URL and an Invalid Token")
    public static RequestSpecification getFakeRequestSpecification(String fakeToken) {
        logger.info("Building Request Specification with a fake Token");
        String baseUrl = ConfigLoader.getInstance().getBaseUrl();

        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addFilter(new io.qameta.allure.restassured.AllureRestAssured())
                .addHeader("Authorization", "Bearer " + fakeToken)
                .log(LogDetail.ALL)
                .build();
    }

    @Step("Generating Response Specification with Expect Content Type JSON")
    public static ResponseSpecification getResponseSpec() {
        logger.info("Building Response Specification for a new API call");
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
}
