package com.mkobo.mkobotask.payloads.request;

import com.mkobo.mkobotask.validator.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateUserDetailsRequest {
    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @Email
    private String emailAddress;

    @NotNull
    @NotEmpty
    @Size()
    private String phoneNumber;

    @ValidPassword
    private String password;

    @ValidPassword
    private String confirmPassword;

}
