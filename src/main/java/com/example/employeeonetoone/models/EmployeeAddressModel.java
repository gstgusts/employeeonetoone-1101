package com.example.employeeonetoone.models;

import lombok.Data;

@Data
public class EmployeeAddressModel {
    private String firstName;
    private String lastName;
    private Integer salary;
    private Boolean useExistingAddress;
    private Integer addressId;
    private String street;
    private String city;
    private String state;
    private String zip;
}
