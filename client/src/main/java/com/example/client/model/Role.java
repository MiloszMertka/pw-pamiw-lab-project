package com.example.client.model;

public enum Role {
    USER,
    ADMIN;

    public static Role fromString(String role) {
        return switch (role) {
            case "USER" -> USER;
            case "ADMIN" -> ADMIN;
            default -> throw new IllegalArgumentException("Invalid role");
        };
    }
}
