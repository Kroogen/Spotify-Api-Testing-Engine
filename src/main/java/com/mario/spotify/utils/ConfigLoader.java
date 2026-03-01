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
        return properties.getProperty("client_id");
    }

    public String getClientSecret() {
        return properties.getProperty("client_secret");
    }

    public String getAccountsUrl() {
        return properties.getProperty("accounts_url");
    }

    public String getBaseUrl() {
        return properties.getProperty("base_url");
    }
}
