package com.example.client.service.internal;

import com.example.client.Endpoints;
import com.example.client.ErrorHandler;
import com.example.client.model.*;
import com.example.client.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;

public class AuthServiceImpl implements AuthService {

    private static final String LOGIN_ENDPOINT = Endpoints.LOGIN.getEndpoint();
    private static final String REGISTER_ENDPOINT = Endpoints.REGISTER.getEndpoint();
    private static final String INVALID_CREDENTIALS_MESSAGE = "Invalid credentials";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Jwt login(LoginUser loginUser) {
        try {
            final var uri = new URI(LOGIN_ENDPOINT);
            final var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(loginUser)))
                    .setHeader("Content-Type", "application/json")
                    .build();
            final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            handleLoginErrors(response);
            final var body = response.body();
            return decodeJwt(body);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void register(RegisterUser registerUser) {
        try {
            final var uri = new URI(REGISTER_ENDPOINT);
            final var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(registerUser)))
                    .setHeader("Content-Type", "application/json")
                    .build();
            final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            handleRegisterErrors(response);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private Jwt decodeJwt(String jwt) {
        try {
            final var token = objectMapper.readTree(jwt).get("token").asText();
            final var payload = token.split("\\.")[1];
            final var decoder = Base64.getDecoder();
            final var decodedPayload = decoder.decode(payload);
            final var role = objectMapper.readTree(decodedPayload).get("authorities").get(0).get("authority").asText();
            return new Jwt(token, Role.fromString(role));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private void handleLoginErrors(HttpResponse<String> response) {
        final var statusCode = response.statusCode();
        if (statusCode == 400 || statusCode == 401 || statusCode == 403) {
            ErrorHandler.handle(List.of(INVALID_CREDENTIALS_MESSAGE));
        }
    }

    private void handleRegisterErrors(HttpResponse<String> response) {
        try {
            final var body = response.body();
            final var errors = objectMapper.readValue(body, Errors.class).errors();
            if (errors.isEmpty()) {
                return;
            }
            ErrorHandler.handle(errors);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
