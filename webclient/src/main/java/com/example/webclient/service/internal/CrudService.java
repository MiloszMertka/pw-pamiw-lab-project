package com.example.webclient.service.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CrudService<T> {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl;

    public List<T> getAll(String url, Class<T> clazz) {
        try {
            final var uri = new URI(baseUrl + url);
            final var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
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
            final var uri = new URI(baseUrl + url);
            final var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(entity)))
                    .setHeader("Content-Type", "application/json")
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void update(String url, T entity) {
        try {
            final var uri = new URI(baseUrl + url);
            final var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(entity)))
                    .setHeader("Content-Type", "application/json")
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void delete(String url) {
        try {
            final var uri = new URI(baseUrl + url);
            final var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .DELETE()
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
