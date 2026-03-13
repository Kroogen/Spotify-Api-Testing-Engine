package com.mario.spotify.tests;

import com.mario.spotify.api.PlaylistApi;
import com.mario.spotify.models.ErrorResponse;
import com.mario.spotify.models.Playlist;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Feature("Negative Tests")
public class PlaylistNegativeTests {

    private static final Logger logger = LogManager.getLogger(PlaylistNegativeTests.class);
    private PlaylistApi playlistApi;

    @BeforeClass
    public void setUp() {
        playlistApi = new PlaylistApi();
    }

    @Story("Invalid Token")
    @Description("This test try to create a Playlist with a fake Token, the Playlist shoudn't be created and must get a HTTP 401 Error")
    @Test
    public void testInvalidToken() {
        logger.info("--- Starting test: testInvalidToken");
        String faketoken = "BQDEiq_uRnr9M3gK_8G0VPc5PU5nGKJ5tBVFoqGzs0Hu1r_bcUsXVf2vW32iKiYYJ6Xt3Lca266rITpdkfE-XR7dVOQlgdS6gzWepnGF2wfadRF_cRHLAn4HaV4ySD0rUD_vlrmt44yAlx0PQxSR9H2PevBQ61s2mx4wdGoISCGV8X30gcpiikKDVUtFXTWKooWOQD2CwLf5nN35iTIyCWOl0pZTu5iEibBSQLe3N5d7ELZkbXrDoS6D4ovINjeVgegrOWWsFK7aU4Kdyb2eI-VYa";
        String expectedErrorMessage = "Invalid access token";

        Playlist playlist = Playlist
                .builder()
                .name("Fake Playlist")
                .description("This Playlist shouldn't exist")
                .isPublic(true)
                .build();

        Response response = playlistApi.postPlaylist(playlist, faketoken);
        response.then()
                .statusCode(401);

        String errorMessage = response.getBody().as(ErrorResponse.class).getErrorDetails().getMessage();
        Assert.assertEquals(errorMessage,
                expectedErrorMessage,
                "The error message is " + errorMessage + " but should be: " + expectedErrorMessage);
        logger.info("--- Completed test: testInvalidToken");
    }

    @Story("Retreive an unexistent playlist")
    @Description("This test try to get an unexistent playlist with a fake playlist id, must get a HTTP 404 Error")
    @Test
    public void testInvalidPlaylistId() {
        logger.info("--- Starting test: testInvalidPlaylistId");
        String fakePlaylistId = "6tzliQB4SNOTREALT4tfxe";
        String expectedErrorMessage = "Resource not found";

        Response response = playlistApi.getPlaylist(fakePlaylistId);
        response.then()
                .statusCode(404);
        String errorMessage = response.getBody().as(ErrorResponse.class).getErrorDetails().getMessage();
        Assert.assertEquals(errorMessage,
                expectedErrorMessage,
                "The error message is " + errorMessage + " but should be: " + expectedErrorMessage);
        logger.info("--- Completed test: testInvalidPlaylistId");
    }

    @Story("Malformed playlist creation")
    @Description("The test try to create a playlist without name, must get a HTTP 400 Error")
    @Test
    public void testMalformedPlaylist() {
        logger.info("--- Starting test: testMalformedPlaylist");
        String expectedErrorMessage = "Missing required field: name";
        Playlist playlist = Playlist
                .builder()
                .name(null)
                .description("This Playlist shouldn't exist")
                .isPublic(true)
                .build();

        Response response = playlistApi.postPlaylist(playlist);
        response.then()
                .statusCode(400);
        String errorMessage = response.getBody().as(ErrorResponse.class).getErrorDetails().getMessage();
        Assert.assertEquals(errorMessage,
                expectedErrorMessage,
                "The error message is " + errorMessage + " but should be: " + expectedErrorMessage);
        logger.info("--- Completed test: testMalformedPlaylist");
    }
}
