package com.mkobo.mkobotask.service;

import com.mkobo.mkobotask.payloads.request.UpdateUserDetailsRequest;
import com.mkobo.mkobotask.payloads.response.ApiResponse;

public interface UserAccountService {
    ApiResponse updateUserDetails(UpdateUserDetailsRequest updateUserDetailsRequest);

}
