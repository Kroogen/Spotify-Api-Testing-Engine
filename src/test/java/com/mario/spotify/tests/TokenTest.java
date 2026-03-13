package com.mario.spotify.tests;

import com.mario.spotify.utils.TokenManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Access Token Validation")
public class TokenTest {

    private static final Logger logger = LogManager.getLogger(TokenTest.class);

    // Quick smoke test to validate a valid Token creation
    @Story("Validate Token creation")
    @Description("This test validate the token created is not null")
    @Test
    public void testToken() {
        logger.info("--- Starting test: testToken");
        Assert.assertNotNull(TokenManager.getToken());
        logger.info("--- Completed test: testToken");
    }
}
