package com.example.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public enum Endpoints {

    CARS("cars"),
    ENGINES("engines"),
    EQUIPMENT_OPTIONS("equipmentOptions"),
    LOGIN("login"),
    REGISTER("register");

    private static final String ENDPOINTS_FILE = "endpoints.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonNode endpointsJson = readEndpointsJson();
    private static final String baseUrl = endpointsJson.get("baseUrl").asText();
    private static final JsonNode endpoints = endpointsJson.get("endpoints");
    private final String endpointName;

    Endpoints(String endpointName) {
        this.endpointName = endpointName;
    }

    private static JsonNode readEndpointsJson() {
        try (final var inputStream = Endpoints.class.getResourceAsStream(ENDPOINTS_FILE)) {
            final var json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return objectMapper.readTree(json);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new IllegalStateException(exception.getMessage());
        }
    }

    public String getEndpoint() {
        return baseUrl + endpoints.get(endpointName).asText();
    }

}
