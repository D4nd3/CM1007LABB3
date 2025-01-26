package com.example.backenduser.dto.responses.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LocationResponse {
    private int id;

    private String name;

    private String address;
}