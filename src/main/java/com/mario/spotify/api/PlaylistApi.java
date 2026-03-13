package com.mario.spotify.api;

import com.mario.spotify.models.Playlist;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PlaylistApi {
    private final String ME_PLAYLISTS = "/me/playlists";
    private final String GET_PLAYLISTS = "/playlists";
    private final String UNFOLLOW_PLAYLIST = "/followers";
    private final String ADD_PLAYLIST_PATH = "/items";

    // Post method
    @Step("Creating a new Playlist")
    public Response postPlaylist(Playlist requestPlaylist) {
        RequestSpecification requestSpecification = SpecBuilder.getRequestSpecification();
        ResponseSpecification responseSpecification = SpecBuilder.getResponseSpec();

        return RestAssured
                .given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(requestPlaylist)
                .when()
                .post(ME_PLAYLISTS)
                .then()
                .spec(responseSpecification)
                .extract().response();
    }

    // Post method used for negative tests Scenarios
    @Step("Try to create a playlist with a wrong Token")
    public Response postPlaylist(Playlist requestPlaylist, String token) {
        RequestSpecification requestSpecification = SpecBuilder.getFakeRequestSpecification(token);
        ResponseSpecification responseSpecification = SpecBuilder.getResponseSpec();

        return RestAssured
                .given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(requestPlaylist)
                .when()
                .post(ME_PLAYLISTS)
                .then()
                .spec(responseSpecification)
                .extract().response();
    }

    // Get method
    @Step("Retrieving playlist with id: {0}")
    public Response getPlaylist(String id) {
        RequestSpecification requestSpecification = SpecBuilder.getRequestSpecification();
        ResponseSpecification responseSpecification = SpecBuilder.getResponseSpec();

        return RestAssured
                .given()
                .spec(requestSpecification)
                .when()
                .get(GET_PLAYLISTS + "/" + id)
                .then()
                .spec(responseSpecification)
                .extract().response();
    }

    // Put Method
    @Step("Update the Playlist {playlist}")
    public Response updatePlaylist(String id, Playlist playlist) {
        RequestSpecification requestSpecification = SpecBuilder.getRequestSpecification();

        return RestAssured
                .given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(playlist)
                .when()
                .put(GET_PLAYLISTS + "/" + id)
                .then()
                .extract().response();
    }

    // Delete Method
    @Step("Unlink the Playlist {id} to the account")
    public Response deletePlaylist(String id) {
        RequestSpecification requestSpecification = SpecBuilder.getRequestSpecification();

        return RestAssured
                .given()
                .spec(requestSpecification)
                .when()
                .delete(GET_PLAYLISTS + "/" + id + UNFOLLOW_PLAYLIST)
                .then()
                .extract().response();
    }

    // Add a track in the playlist
    @Step("Link the Track: {1} with the Playlist {0}")
    public Response postTrackInPlaylist(String playlistId, String trackUri) {
        RequestSpecification requestSpecification = SpecBuilder.getRequestSpecification();
        ResponseSpecification responseSpecification = SpecBuilder.getResponseSpec();

        return RestAssured
                .given()
                .spec(requestSpecification)
                .queryParam("uris", trackUri)
                .when()
                .post(GET_PLAYLISTS + "/" + playlistId + ADD_PLAYLIST_PATH)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }

}
