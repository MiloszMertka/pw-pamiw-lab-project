package com.example.client.model;

public record Engine(
        Long id,
        String name,
        Integer horsePower
) {

    @Override
    public String toString() {
        return name + " " + horsePower + " HP";
    }

}
