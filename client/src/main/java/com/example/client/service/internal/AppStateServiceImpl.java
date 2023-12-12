package com.example.client.service.internal;

import com.example.client.model.Role;
import com.example.client.service.AppStateService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.prefs.Preferences;

public class AppStateServiceImpl implements AppStateService {

    private static final String JWT_TOKEN_KEY = "jwtToken";
    private static final String IS_DARK_MODE_KEY = "isDarkMode";
    private static final String LANGUAGE_CODE_KEY = "languageCode";
    private final Preferences preferences = Preferences.userRoot().node("com.example.client");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void storeJwtToken(String jwtToken) {
        preferences.put(JWT_TOKEN_KEY, jwtToken);
    }

    @Override
    public String getJwtToken() {
        return preferences.get(JWT_TOKEN_KEY, null);
    }

    @Override
    public void clearJwtToken() {
        preferences.remove(JWT_TOKEN_KEY);
    }

    @Override
    public void storeIsDarkMode(boolean isDarkMode) {
        preferences.putBoolean(IS_DARK_MODE_KEY, isDarkMode);
    }

    @Override
    public boolean getIsDarkMode() {
        return preferences.getBoolean(IS_DARK_MODE_KEY, false);
    }

    @Override
    public void storeLanguageCode(String languageCode) {
        preferences.put(LANGUAGE_CODE_KEY, languageCode);
    }

    @Override
    public String getLanguageCode() {
        return preferences.get(LANGUAGE_CODE_KEY, "en");
    }

    @Override
    public boolean isAdmin() {
        final var jwtToken = getJwtToken();

        if (jwtToken == null) {
            return false;
        }

        final var role = getRoleFromJwt(jwtToken);
        return role == Role.ADMIN;
    }

    private Role getRoleFromJwt(String jwt) {
        try {
            final var payload = jwt.split("\\.")[1];
            final var decoder = Base64.getDecoder();
            final var decodedPayload = decoder.decode(payload);
            final var role = objectMapper.readTree(decodedPayload).get("authorities").get(0).get("authority").asText();
            return Role.fromString(role);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
