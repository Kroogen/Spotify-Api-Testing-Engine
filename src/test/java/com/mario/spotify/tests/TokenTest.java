package com.mario.spotify.tests;

import com.mario.spotify.utils.TokenManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TokenTest {

    // Quick smoke test to validate a valid Token creation
    @Test
    public void testToken() {
        Assert.assertNotNull(TokenManager.getToken());
    }
}
