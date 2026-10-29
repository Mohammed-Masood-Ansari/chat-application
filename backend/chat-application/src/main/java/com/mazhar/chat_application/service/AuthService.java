package com.mazhar.chat_application.service;

import com.mazhar.chat_application.dto.request.LoginRequest;
import com.mazhar.chat_application.dto.request.RegisterRequest;
import com.mazhar.chat_application.dto.response.AuthResponse;

public interface AuthService {

	String register(RegisterRequest registerRequest);

	AuthResponse login(LoginRequest loginRequest);

}