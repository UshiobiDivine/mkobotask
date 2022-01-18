package com.mkobo.mkobotask.controller;

import com.mkobo.mkobotask.payloads.request.AccountCreationRequest;
import com.mkobo.mkobotask.payloads.request.LoginRequest;
import com.mkobo.mkobotask.payloads.response.ApiResponse;
import com.mkobo.mkobotask.security.JwtAuthenticationResponse;
import com.mkobo.mkobotask.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody AccountCreationRequest accountCreationRequest) {
        if (authService.signUp(accountCreationRequest)){
            return new ResponseEntity<>(new ApiResponse(true, "User Registration Successful"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Error creating user"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
