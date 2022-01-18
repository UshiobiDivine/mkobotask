package com.mkobo.mkobotask.payloads.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private boolean success;
    private String message;
    private int httpResponseCode;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, int httpResponseCode, String message) {
        this.success = success;
        this.message = message;
        this.httpResponseCode=httpResponseCode;
    }

}
