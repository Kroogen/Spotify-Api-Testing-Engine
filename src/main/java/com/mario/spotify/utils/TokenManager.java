package com.mario.spotify.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TokenManager {

    public static String getToken() {
        String clientId = ConfigLoader.getInstance().getClientId();
        String clientSecret = ConfigLoader.getInstance().getClientSecret();
        String accountsUrl = ConfigLoader.getInstance().getAccountsUrl();

        Response response = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .when()
                .post(accountsUrl + "/api/token")
                .then()
                .statusCode(200)
                .extract()
                .response();

        return response.path("access_token");
    }
}