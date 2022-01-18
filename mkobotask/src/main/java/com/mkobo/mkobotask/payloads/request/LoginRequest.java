package com.mkobo.mkobotask.payloads.request;

import com.mkobo.mkobotask.validator.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @Email
    @NotBlank(message = "email cannot be empty")
    @NotNull(message = "email cannot be null")
    private String email;
    @NotBlank(message = "password cannot be empty")
    @NotNull(message = "password cannot be null")
    @ValidPassword
    private String password;
}
