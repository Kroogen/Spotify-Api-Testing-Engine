package com.mario.spotify.tests;

import com.mario.spotify.api.AccountApi;
import com.mario.spotify.api.PlaylistApi;
import com.mario.spotify.models.Playlist;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Feature("Playlist API")
public class PlaylistTests {

    private static final Logger logger = LogManager.getLogger(PlaylistTests.class);
    private String playlistId;
    private PlaylistApi playlistApi;
    private String expectedPlaylistName = "Test Playlist";
    private String expectedDescription = "Description for Test Playlist";
    private Boolean expectedPublic = true;
    private String expectedUserId;
    private String expectedType = "playlist";
    private int expectedItems = 0;

    @BeforeClass
    public void setup() {
        playlistApi = new PlaylistApi();
        expectedUserId = AccountApi.getUserProfile().path("id");
    }

    @Story("Create a Playlist")
    @Description("This test validates that a user can successfully create a new playlist with name and description")
    @Test(priority = 1)
    public void testCreatePlaylist() {
        logger.info("--- Starting test: testCreatePlaylist");
        Playlist playlist = Playlist
                .builder()
                .name(expectedPlaylistName)
                .description(expectedDescription)
                .isPublic(expectedPublic)
                .build();

        Response response = playlistApi.postPlaylist(playlist);

        response
                .then()
                .statusCode(201);

        playlistId = response.path("id");
        String actualName = response.path("name");
        String actualDescription = response.path("description");
        Boolean actualPublic = response.path("public");
        String actualType = response.path("type");
        String actualOwnerId = response.path("owner.id");
        int actualTotalItems = response.path("items.total");

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actualName,
                expectedPlaylistName,
                "The name of the Playlist is: " + actualName + " but should be: " + expectedPlaylistName);
        softAssert.assertEquals(actualDescription,
                expectedDescription,
                "The description of the Playlist is: " + actualDescription + " but should be: " + expectedDescription);
        softAssert.assertEquals(actualPublic,
                expectedPublic,
                "The public status of the Playlist is: " + actualPublic + " but should be: " + expectedPublic);
        softAssert.assertEquals(actualType,
                expectedType,
                "The type of the Playlist is: " + actualType + " but should be: " + expectedType);
        softAssert.assertEquals(actualOwnerId,
                expectedUserId,
                "The actual User id of the Playlist is: " + actualOwnerId + " but should be: " + expectedUserId);
        softAssert.assertEquals(actualTotalItems,
                expectedItems,
                "The total items in the Playlist is: " + actualTotalItems + " but should be: " + expectedItems);

        softAssert.assertAll();
        logger.info("--- Completed test: testCreatePlaylist");
    }

    @Story("Validate playlist creation")
    @Description("This test validate the user can retrieve the playlist created")
    @Test(priority = 2, dependsOnMethods = "testCreatePlaylist")
    public void testGetPlaylist() {
        logger.info("--- Starting test: testGetPlaylist");
        Response response = playlistApi.getPlaylist(playlistId);
        response
                .then()
                .statusCode(200);
        String actualName = response.path("name");
        String actualDescription = response.path("description");
        Boolean actualPublic = response.path("public");
        String actualType = response.path("type");
        String actualOwnerId = response.path("owner.id");
        int actualTotalItems = response.path("items.total");

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actualName,
                expectedPlaylistName,
                "The name of the Playlist is: " + actualName + " but should be: " + expectedPlaylistName);
        softAssert.assertEquals(actualDescription,
                expectedDescription,
                "The description of the Playlist is: " + actualDescription + " but should be: " + expectedDescription);
        softAssert.assertEquals(actualPublic,
                expectedPublic,
                "The public status of the Playlist is: " + actualPublic + " but should be: " + expectedPublic);
        softAssert.assertEquals(actualType,
                expectedType,
                "The type of the Playlist is: " + actualType + " but should be: " + expectedType);
        softAssert.assertEquals(actualOwnerId,
                expectedUserId,
                "The actual User id of the Playlist is: " + actualOwnerId + " but should be: " + expectedUserId);
        softAssert.assertEquals(actualTotalItems,
                expectedItems,
                "The total items in the Playlist is: " + actualTotalItems + " but should be: " + expectedItems);
        softAssert.assertAll();
        logger.info("--- Completed test: testGetPlaylist");
    }

    @Story("Update playlist information")
    @Description("This test validate the user is able to change the information in the playlist")
    @Test(priority = 3, dependsOnMethods = "testCreatePlaylist")
    public void testUpdatePlaylist() {
        logger.info("--- Starting test: testUpdatePlaylist");
        String expectedNewName = "Playlist v2";
        String expectedNewDescription = "Playlist modified";

        Playlist playlist = Playlist
                .builder()
                .name(expectedNewName)
                .description(expectedNewDescription)
                .isPublic(expectedPublic)
                .build();

        Response response = playlistApi.updatePlaylist(playlistId, playlist);
        response.then()
                .statusCode(200);

        response = playlistApi.getPlaylist(playlistId);
        response
                .then()
                .statusCode(200);

        String actualName = response.path("name");
        String actualDescription = response.path("description");
        Boolean actualPublic = response.path("public");
        String actualType = response.path("type");
        String actualOwnerId = response.path("owner.id");
        int actualTotalItems = response.path("items.total");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualName,
                expectedNewName,
                "The name of the Playlist is: " + actualName + " but should be: " + expectedNewName);
        softAssert.assertEquals(actualDescription,
                expectedNewDescription,
                "The description of the Playlist is: " + actualDescription + " but should be: " + expectedNewDescription);
        softAssert.assertEquals(actualPublic,
                expectedPublic,
                "The  public status of the Playlist is: " + actualPublic + " but should be: " + expectedPublic);
        softAssert.assertEquals(actualType,
                expectedType,
                "The type of the Playlist is: " + actualType + " but should be: " + expectedType);
        softAssert.assertEquals(actualOwnerId,
                expectedUserId,
                "The actual User id of the Playlist is: " + actualOwnerId + " but should be: " + expectedUserId);
        softAssert.assertEquals(actualTotalItems,
                expectedItems,
                "The total items in  the Playlist is: " + actualTotalItems + " but should be: " + expectedItems);
        softAssert.assertAll();
        logger.info("--- Completed test: testUpdatePlaylist");
    }

    @Story("Unlink playlist")
    @Description("The test validate the user can unlink the playlist from its account")
    @Test(priority = 4, dependsOnMethods = "testCreatePlaylist")
    public void testDeletePlaylist() {
        logger.info("--- Starting test: testDeletePlaylist");
        Response response = playlistApi.deletePlaylist(playlistId);
        response
                .then()
                .statusCode(200);
        logger.info("--- Completed test: testDeletePlaylist");
    }
}
