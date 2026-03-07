/*
 * Script used to generate a new "access_token"
 * for each API call
 * */

package com.mario.spotify.utils;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class TokenManager {
    private static String access_token;

    public static String getToken() {
        if (access_token == null) {
            access_token = renewToken();
        }
        return access_token;
    }

    private static String renewToken() {
        String clientId = ConfigLoader.getInstance().getClientId();
        String clientSecret = ConfigLoader.getInstance().getClientSecret();
        String refreshToken = ConfigLoader.getInstance().getRefreshToken();
        String accountsUrl = ConfigLoader.getInstance().getAccountsUrl();

        // Codificamos las credenciales en Base64
        String base64Credentials = Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes());

        return given()
                .header("Authorization", "Basic " + base64Credentials)
                .formParam("grant_type", "refresh_token")
                .formParam("refresh_token", refreshToken)
                .when()
                .post(accountsUrl + "/api/token")
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");
    }
}