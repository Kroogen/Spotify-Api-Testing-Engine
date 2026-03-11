package com.mario.spotify.tests;

import com.mario.spotify.api.PlaylistApi;
import com.mario.spotify.models.Playlist;
import com.mario.spotify.utils.ConfigLoader;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PlaylistTests {

    private String playlistId;
    private PlaylistApi playlistApi;
    private String expectedPlaylistName = "Test Playlist";
    private String expectedDescription = "Description for Test Playlist";
    private Boolean expectedPublic = true;
    private String expectedUserId = ConfigLoader.getInstance().getUserId();
    private String expectedType = "playlist";
    private int expectedItems = 0;

    @BeforeClass
    public void setup() {
        playlistApi = new PlaylistApi();
    }

    @Test(priority = 1)
    public void testCreatePlaylist() {

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
    }

    @Test(priority = 2, dependsOnMethods = "testCreatePlaylist")
    public void testGetPlaylist() {
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
    }

    @Test(priority = 3, dependsOnMethods = "testCreatePlaylist")
    public void testUpdatePlaylist() {
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
    }

    @Test(priority = 4, dependsOnMethods = "testCreatePlaylist")
    public void testDeletePlaylist() {
        Response response = playlistApi.deletePlaylist(playlistId);
        response
                .then()
                .statusCode(200);
    }
}
