package com.example.client.service.internal;

import com.example.client.service.AppStateService;

import java.util.prefs.Preferences;

public class AppStateServiceImpl implements AppStateService {

    private static final String JWT_TOKEN_KEY = "jwtToken";
    private static final String IS_DARK_MODE_KEY = "isDarkMode";
    private static final String LANGUAGE_CODE_KEY = "languageCode";
    private final Preferences preferences = Preferences.userRoot().node("com.example.client");

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


}
