package com.mkobo.mkobotask.service.impl;

import com.mkobo.mkobotask.models.UserAccount;
import com.mkobo.mkobotask.payloads.request.UpdateUserDetailsRequest;
import com.mkobo.mkobotask.payloads.response.ApiResponse;
import com.mkobo.mkobotask.repository.UserAccountRepository;
import com.mkobo.mkobotask.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class UserAccountServiceImpl implements UserAccountService {

    private PasswordEncoder passwordEncoder;
    private UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(PasswordEncoder passwordEncoder, UserAccountRepository userAccountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public ApiResponse updateUserDetails(UpdateUserDetailsRequest updateUserDetailsRequest) {

        UserAccount userAccount = userAccountRepository.findByEmail(updateUserDetailsRequest.getEmailAddress());

        if (userAccount!=null){

            userAccount.setFirstName(updateUserDetailsRequest.getFirstName());
            userAccount.setLastName(updateUserDetailsRequest.getLastName());
            userAccount.setPassword(passwordEncoder.encode(updateUserDetailsRequest.getPassword()));
            return new ApiResponse(true,200, "User details Updated");
        }
        return new ApiResponse(false, 400, "User does not exist");
    }
}
