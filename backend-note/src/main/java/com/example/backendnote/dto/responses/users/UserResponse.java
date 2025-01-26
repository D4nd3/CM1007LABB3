package com.example.backendnote.dto.responses.users;

import com.example.backendnote.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
public class UserResponse {
    private int id;
    private String fullName;
    private Role role;
}

