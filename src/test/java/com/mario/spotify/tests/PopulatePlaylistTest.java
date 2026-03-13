package com.mario.spotify.tests;

import com.mario.spotify.api.PlaylistApi;
import com.mario.spotify.api.SearchApi;
import com.mario.spotify.models.Playlist;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Feature("Populate Playlist")
public class PopulatePlaylistTest {

    private static final Logger logger = LogManager.getLogger(PopulatePlaylistTest.class);
    private PlaylistApi playlistApi;
    private SearchApi searchApi;
    private String playlistId;
    private String trackUri;

    @BeforeClass
    public void setUp() {
        playlistApi = new PlaylistApi();
        searchApi = new SearchApi();
    }

    @Story("Search a song")
    @Description("Given the track name the api should return it URI")
    @Test(priority = 1)
    public void testTrackSearching() {
        logger.info("--- Starting test: testTrackSearching");
        String trackName = "bohemian rhapsody";
        String actualTrackName;

        Response response = searchApi.getTrackId(trackName);
        response.then()
                .statusCode(200);
        actualTrackName = response.path("tracks.items[0].name");
        Assert.assertTrue(actualTrackName.toLowerCase().contains(trackName),
                "The actual track name is: " + actualTrackName + " but should be: " + trackName);

        trackUri = response.path("tracks.items[0].uri");
        logger.info("--- Completed test: testTrackSearching");
    }

    @Story("Create a playlist")
    @Description("This test validate the correct creation of a playlist where to add a track on it")
    @Test(priority = 2, dependsOnMethods = "testTrackSearching")
    public void testPlaylistCreation() {
        logger.info("--- Starting test: testPlaylistCreation");
        Response response = playlistApi.postPlaylist(createPlaylist());
        response.then()
                .statusCode(201);
        playlistId = response.path("id");
        logger.info("--- Completed test: testPlaylistCreation");
    }

    @Story("Adding track on Playlist")
    @Description("The test validate the track could be linked in the playlist")
    @Test(priority = 3, dependsOnMethods = {"testTrackSearching", "testPlaylistCreation"})
    public void testAddingTrack() {
        logger.info("--- Starting test: testAddingTrack");
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
        logger.info("--- Completed test: testAddingTrack");
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
