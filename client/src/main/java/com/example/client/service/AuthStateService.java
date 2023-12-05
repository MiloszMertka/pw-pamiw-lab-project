package com.example.client.service;

public interface AuthStateService {

    void storeJwtToken(String jwtToken);

    String getJwtToken();

    void clearJwtToken();

}
