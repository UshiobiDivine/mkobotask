package com.mkobo.mkobotask.payloads.response;

import lombok.Data;

@Data
public class UserAccountResponse {

    private Long userAccountId;
    private String email;

    private String phoneNumber;

    private String employmentCategory;
    private String firstName;
    private String lastName;

    private String dateOfBirth;
    private String country;
    private String state;
    private String city;
    private String residentialAddress;
    private String employmentType;
    private String timeSpentOnJob;
    private String cummulativeExperienceYears;
    private String currentMonthlySalary;
    private String payday;

}
