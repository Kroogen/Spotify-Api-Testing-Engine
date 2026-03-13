package com.mario.spotify.tests;

import com.mario.spotify.api.PlaylistApi;
import com.mario.spotify.models.ErrorResponse;
import com.mario.spotify.models.Playlist;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PlaylistNegativeTests {

    private PlaylistApi playlistApi;

    @BeforeClass
    public void setUp() {
        playlistApi = new PlaylistApi();
    }

    @Test
    public void testInvalidToken() {
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

    }

    @Test
    public void testInvalidPlaylistId() {
        String fakePlaylistId = "6tzliQB4SNOTREALT4tfxe";
        String expectedErrorMessage = "Resource not found";

        Response response = playlistApi.getPlaylist(fakePlaylistId);
        response.then()
                .statusCode(404);
        String errorMessage = response.getBody().as(ErrorResponse.class).getErrorDetails().getMessage();
        Assert.assertEquals(errorMessage,
                expectedErrorMessage,
                "The error message is " + errorMessage + " but should be: " + expectedErrorMessage);
    }

    @Test
    public void testMalformedPlaylist() {
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
    }
}
