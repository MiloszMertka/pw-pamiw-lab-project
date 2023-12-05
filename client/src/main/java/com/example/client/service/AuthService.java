package com.example.client.service;

import com.example.client.model.Jwt;
import com.example.client.model.LoginUser;
import com.example.client.model.RegisterUser;

public interface AuthService {

    Jwt login(LoginUser loginUser);

    void register(RegisterUser registerUser);

}
