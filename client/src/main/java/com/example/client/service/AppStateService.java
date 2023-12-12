package com.example.client.service;

public interface AppStateService {

    void storeJwtToken(String jwtToken);

    String getJwtToken();

    void clearJwtToken();

    void storeIsDarkMode(boolean isDarkMode);

    boolean getIsDarkMode();

    void storeLanguageCode(String languageCode);

    String getLanguageCode();

    boolean isAdmin();

}
