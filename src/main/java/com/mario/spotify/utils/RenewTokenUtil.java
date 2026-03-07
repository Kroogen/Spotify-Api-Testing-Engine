/*
 * Script to generate a new refresh_token used as
 * authorization Bearer Token
 * */
package com.mario.spotify.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RenewTokenUtil {

    public static void main(String[] args) {
        String authCode = ConfigLoader.getInstance().getAuthCode();
        String redirectUri = ConfigLoader.getInstance().getRedirectUri();
        String auth_code = ConfigLoader.getInstance().getAuthCodeGrantType();
        String client_id = ConfigLoader.getInstance().getClientId();
        String client_secret = ConfigLoader.getInstance().getClientSecret();
        String accountsUrl = ConfigLoader.getInstance().getAccountsUrl();

        Response response = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("grant_type", auth_code)
                .formParam("code", authCode)
                .formParam("redirect_uri", redirectUri)
                .formParam("client_id", client_id)
                .formParam("client_secret", client_secret)
                .when()
                .post(accountsUrl + "/api/token")
                .then()
                .log().all()
                .extract()
                .response();

        if (response.statusCode() == 200) {
            System.out.println("\nYour refresh_token is in the JSON above.");
        } else {
            System.out.println("\nERROR: Ups, your one use code is invalid, try with a new one.");
        }
    }
}