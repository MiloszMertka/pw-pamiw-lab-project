package com.example.client.service.internal;

import com.example.client.ErrorHandler;
import com.example.client.model.Errors;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class CrudService<T> {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String jwt;

    public CrudService(String jwt) {
        this.jwt = jwt;
    }

    public List<T> getAll(String url, Class<T> clazz) {
        try {
            final var uri = new URI(url);
            final var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .setHeader("X-Authorization", "Bearer " + jwt)
                    .build();
            final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            final var body = response.body();
            return objectMapper.readValue(body, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void create(String url, T entity) {
        try {
            final var uri = new URI(url);
            final var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(entity)))
                    .setHeader("X-Authorization", "Bearer " + jwt)
                    .setHeader("Content-Type", "application/json")
                    .build();
            final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            handlePossibleErrors(response);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void update(String url, T entity) {
        try {
            final var uri = new URI(url);
            final var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(entity)))
                    .setHeader("X-Authorization", "Bearer " + jwt)
                    .setHeader("Content-Type", "application/json")
                    .build();
            final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            handlePossibleErrors(response);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void delete(String url) {
        try {
            final var uri = new URI(url);
            final var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .DELETE()
                    .setHeader("X-Authorization", "Bearer " + jwt)
                    .build();
            final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            handlePossibleErrors(response);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void handlePossibleErrors(HttpResponse<String> response) {
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
