package com.example.backenduser.dto.responses.users;

import com.example.backenduser.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Role role;
}