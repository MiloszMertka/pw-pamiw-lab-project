package com.example.client.service.internal;

import com.example.client.service.AuthStateService;

import java.util.prefs.Preferences;

public class AuthStateServiceImpl implements AuthStateService {

    private static final String JWT_TOKEN_KEY = "jwtToken";
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

}
