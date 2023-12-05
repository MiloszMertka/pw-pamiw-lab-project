package com.example.api.controller;

import com.example.api.dto.JwtDto;
import com.example.api.dto.LoginUserDto;
import com.example.api.dto.RegisterUserDto;
import com.example.api.service.AuthUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
class AuthController {

    private final AuthUseCases authUseCases;

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody @Valid LoginUserDto loginUserDto) {
        final var jwt = authUseCases.login(loginUserDto);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        authUseCases.register(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
