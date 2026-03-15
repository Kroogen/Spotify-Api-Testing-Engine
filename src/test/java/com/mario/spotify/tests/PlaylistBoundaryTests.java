package com.mario.spotify.tests;

import com.mario.spotify.api.SearchApi;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Feature("Playlist Boundary Testing")
public class PlaylistBoundaryTests {

    private SearchApi searchApi;
    private static final Logger logger = LogManager.getLogger(PlaylistBoundaryTests.class);

    @BeforeClass
    public void setUp() {
        searchApi = new SearchApi();
    }

    @DataProvider(name = "PlaylistNames", parallel = true)
    public Object[][] playlistNames() {
        return new Object[][]{
                {"Queen", "artist", 200},
                {"Sheer Heart Attack", "album", 200},
                {"Bohemian Rhapsody", "track", 200},
                {"", "artist", 400},
                {"' OR 1=1 --", "track", 200}
        };
    }

    @Story("Validate Searching")
    @Description("This test validate the schema and status code against artists, albums and tracks ")
    @Test(dataProvider = "PlaylistNames")
    public void testSearch(String name, String type, int expectedStatusCode) {
        logger.info("--- Starting test: testSearchArtists");
        Response response = searchApi.getItem(name, type);
        response.then()
                .statusCode(expectedStatusCode);
        if (expectedStatusCode != 400)
            switch (type.toLowerCase()) {
                case "artist":
                    validateArtistSchema(response);
                    break;
                case "album":
                    validateAlbumSchema(response);
                    break;
                case "track":
                    validateTrackSchema(response);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported type: " + type);
            }

        logger.info("--- Completed test: testSearchArtists");
    }

    private void validateArtistSchema(Response response) {
        response.then()
                .body(io
                        .restassured
                        .module
                        .jsv
                        .JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("schema/artist-schema.json"));
    }

    private void validateAlbumSchema(Response response) {
        response.then()
                .body(io
                        .restassured
                        .module
                        .jsv
                        .JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("schema/album-schema.json"));
    }

    private void validateTrackSchema(Response response) {
        response.then()
                .body(io
                        .restassured
                        .module
                        .jsv
                        .JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("schema/track-schema.json"));
    }
}
