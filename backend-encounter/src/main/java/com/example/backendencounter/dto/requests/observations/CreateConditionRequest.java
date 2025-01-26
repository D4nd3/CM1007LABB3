package com.example.backendencounter.dto.requests.observations;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateConditionRequest {
    private int observationId;
    private String name;
    private String description;
}
