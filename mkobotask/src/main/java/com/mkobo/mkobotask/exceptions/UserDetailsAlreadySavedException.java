package com.mkobo.mkobotask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserDetailsAlreadySavedException extends RuntimeException  {
    public UserDetailsAlreadySavedException(String message) {
        super(message);
    }

}

