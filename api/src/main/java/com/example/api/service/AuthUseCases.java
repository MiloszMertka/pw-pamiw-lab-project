package com.example.api.service;

import com.example.api.dto.JwtDto;
import com.example.api.dto.LoginUserDto;
import com.example.api.dto.RegisterUserDto;

public interface AuthUseCases {

    JwtDto login(LoginUserDto loginUserDto);

    void register(RegisterUserDto registerUserDto);

}
