package com.mkobo.mkobotask.service;

import com.mkobo.mkobotask.payloads.request.AccountCreationRequest;
import com.mkobo.mkobotask.payloads.request.LoginRequest;
import com.mkobo.mkobotask.payloads.response.ApiResponse;
import com.mkobo.mkobotask.security.JwtAuthenticationResponse;

public interface AuthService {
    boolean signUp(AccountCreationRequest accountCreationRequest);
    JwtAuthenticationResponse login(LoginRequest loginRequest);
}
