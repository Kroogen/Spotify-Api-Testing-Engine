package com.mario.spotify.tests;

import com.mario.spotify.api.PlaylistApi;
import com.mario.spotify.models.Playlist;
import com.mario.spotify.utils.ConfigLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PlaylistTests {

    @Test
    public void testCreatePlaylist() {
        PlaylistApi playlistApi = new PlaylistApi();
        String expectedPlaylistName = "Test Playlist";
        String expectedDescription = "Description for Test Playlist";
        Boolean expectedPublic = true;
        String expectedUserId = ConfigLoader.getInstance().getUserId();
        String expectedType = "playlist";
        int expectedItems = 0;

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

}
