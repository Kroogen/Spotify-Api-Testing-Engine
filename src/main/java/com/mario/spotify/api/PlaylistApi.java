package com.mario.spotify.api;

import com.mario.spotify.models.Playlist;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PlaylistApi {
    private final String ME_PLAYLISTS = "/me/playlists";

    // Post method
    public Response postPlaylist(Playlist requestPlaylist) {
        RequestSpecification requestSpecification = SpecBuilder.getRequestSpecification();
        ResponseSpecification responseSpecification = SpecBuilder.getResponseSpec();

        return RestAssured
                .given()
                .spec(requestSpecification)
                .body(requestPlaylist)
                .when()
                .post(ME_PLAYLISTS)
                .then()
                .spec(responseSpecification)
                .extract().response();
    }

}
