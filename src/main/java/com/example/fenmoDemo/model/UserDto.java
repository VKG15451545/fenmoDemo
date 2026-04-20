package com.example.fenmoDemo.model;

import lombok.Getter;

@Getter
public class UserDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Long userTypeId;
    private Long schoolId;
    private String schoolName;
    private String schoolReg;
    private AddressDto addressDto;
}
