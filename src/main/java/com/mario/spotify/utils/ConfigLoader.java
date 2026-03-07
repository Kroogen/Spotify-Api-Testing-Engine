package com.mario.spotify.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties = new Properties();
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new RuntimeException("No config.properties file found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading config.properties: " + e.getMessage());
        }
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getClientId() {
        return properties.getProperty("client_id").trim();
    }

    public String getClientSecret() {
        return properties.getProperty("client_secret").trim();
    }

    public String getClientGrantType() {
        return properties.getProperty("client_grant_type").trim();
    }

    public String getAccountsUrl() {
        return properties.getProperty("accounts_url").trim();
    }

    public String getBaseUrl() {
        return properties.getProperty("base_url").trim();
    }

    public String getUserId() {
        return properties.getProperty("user_id").trim();
    }

    public String getRefreshToken() {
        return properties.getProperty("refresh_token").trim();
    }

    public String getRedirectUri() {
        return properties.getProperty("redirect_uri").trim();
    }

    public String getAuthCodeGrantType() {
        return properties.getProperty("auth_code_grant_type").trim();
    }

    public String getAuthCode() {
        return properties.getProperty("code").trim();
    }

}
