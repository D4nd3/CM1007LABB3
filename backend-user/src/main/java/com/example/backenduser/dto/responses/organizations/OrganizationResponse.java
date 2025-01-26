package com.example.backenduser.dto.responses.organizations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrganizationResponse {
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
}