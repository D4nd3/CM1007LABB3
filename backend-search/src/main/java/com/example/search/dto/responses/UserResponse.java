package com.example.search.dto.responses;

import com.example.search.enums.Role;

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

