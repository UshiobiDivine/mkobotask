package com.mkobo.mkobotask.controller;

import com.mkobo.mkobotask.payloads.request.LoginRequest;
import com.mkobo.mkobotask.payloads.request.UpdateUserDetailsRequest;
import com.mkobo.mkobotask.payloads.response.ApiResponse;
import com.mkobo.mkobotask.security.JwtAuthenticationResponse;
import com.mkobo.mkobotask.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
public class UserAccountController {

    private UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }
    @PutMapping("/update_details")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody UpdateUserDetailsRequest request) {
        ApiResponse apiResponse = userAccountService.updateUserDetails(request);
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getHttpResponseCode()));
    }
}
