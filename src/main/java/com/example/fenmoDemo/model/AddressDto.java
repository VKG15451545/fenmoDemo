package com.example.fenmoDemo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDto {
    private String line1;
    private String line2;
    private String pincode;
    private String city;
    private String country;
    private String district;
    private String state;

}
