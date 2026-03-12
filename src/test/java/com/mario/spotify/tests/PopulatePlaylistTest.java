package com.mario.spotify.tests;

import com.mario.spotify.api.PlaylistApi;
import com.mario.spotify.api.SearchApi;
import com.mario.spotify.models.Playlist;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PopulatePlaylistTest {

    private PlaylistApi playlistApi;
    private SearchApi searchApi;
    private String playlistId;
    private String trackUri;

    @BeforeClass
    public void setUp() {
        playlistApi = new PlaylistApi();
        searchApi = new SearchApi();
    }

    @Test(priority = 1)
    public void testTrackSearching() {
        String trackName = "bohemian rhapsody";
        String actualTrackName;

        Response response = searchApi.getTrackId(trackName);
        response.then()
                .statusCode(200);
        actualTrackName = response.path("tracks.items[0].name");
        Assert.assertTrue(actualTrackName.toLowerCase().contains(trackName),
                "The actual track name is: " + actualTrackName + " but should be: " + trackName);

        trackUri = response.path("tracks.items[0].uri");
    }

    @Test(priority = 2, dependsOnMethods = "testTrackSearching")
    public void testPlaylistCreation() {
        Response response = playlistApi.postPlaylist(createPlaylist());
        response.then()
                .statusCode(201);
        playlistId = response.path("id");
    }

    @Test(priority = 3, dependsOnMethods = {"testTrackSearching", "testPlaylistCreation"})
    public void testAddingTrack() {
        int expectedItemsTotal = 1;
        int actualItemsTotal;

        Response response = playlistApi.postTrackInPlaylist(playlistId, trackUri);
        response.then()
                .statusCode(201);

        response = playlistApi.getPlaylist(playlistId);
        response.then()
                .statusCode(200);
        actualItemsTotal = response.path("items.total");
        Assert.assertEquals(actualItemsTotal,
                expectedItemsTotal,
                "The actual items total is: " + actualItemsTotal + " but should be: " + expectedItemsTotal);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (playlistId != null) {
            Response response = playlistApi.deletePlaylist(playlistId);
            response.then()
                    .statusCode(200);
        }
    }

    private Playlist createPlaylist() {
        return Playlist
                .builder()
                .name("PopulatePlaylist")
                .description("Playlist to validate the populate scenarios")
                .isPublic(true)
                .build();
    }
}
