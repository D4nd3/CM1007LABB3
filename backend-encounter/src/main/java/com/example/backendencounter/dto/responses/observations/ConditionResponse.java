package com.example.backendencounter.dto.responses.observations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ConditionResponse {
    private int id;
    private String name;
    private String description;
}
