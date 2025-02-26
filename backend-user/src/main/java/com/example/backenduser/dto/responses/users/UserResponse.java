package com.example.backenduser.dto.responses.users;

import com.example.backenduser.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String fullName;
    private Role role;
}

