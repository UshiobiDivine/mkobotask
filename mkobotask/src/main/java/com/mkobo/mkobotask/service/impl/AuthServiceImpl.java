package com.mkobo.mkobotask.service.impl;

import com.mkobo.mkobotask.exceptions.BadRequestException;
import com.mkobo.mkobotask.exceptions.PasswordsMismatchException;
import com.mkobo.mkobotask.models.UserAccount;
import com.mkobo.mkobotask.payloads.request.AccountCreationRequest;
import com.mkobo.mkobotask.payloads.request.LoginRequest;
import com.mkobo.mkobotask.repository.UserAccountRepository;
import com.mkobo.mkobotask.security.JwtAuthenticationResponse;
import com.mkobo.mkobotask.security.TokenProvider;
import com.mkobo.mkobotask.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private UserAccountRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenProvider jwtTokenProvider;
    private UserAccountRepository userAccountRepository;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserAccountRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider jwtTokenProvider, UserAccountRepository userAccountRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public boolean signUp(AccountCreationRequest accountCreationRequest) {

        if (!accountCreationRequest.getPassword().equals(accountCreationRequest.getConfirmPassword())) {
            throw new PasswordsMismatchException("Passwords do not match");
        }

        UserAccount byEmail = userRepository.findByEmail(accountCreationRequest.getEmailAddress());
        if (byEmail !=null){
            throw new BadRequestException("Email is already taken");
        }

        UserAccount user = new UserAccount();
        user.setEmail(accountCreationRequest.getEmailAddress().toLowerCase());
        user.setFirstName(accountCreationRequest.getFirstName().toLowerCase());
        user.setLastName(accountCreationRequest.getLastName().toLowerCase());
        user.setPassword(passwordEncoder.encode(accountCreationRequest.getPassword()));

        UserAccount result = userRepository.save(user);

        return  result!=null ? true : false;

    }

    @Override
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {

        if(userAccountRepository.findByEmail(loginRequest.getEmail())==null){
            throw new BadRequestException("User does not exist");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.createToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }
}
