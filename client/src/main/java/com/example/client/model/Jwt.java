package com.example.client.model;

public record Jwt(
        String token,
        Role role
) {

}
