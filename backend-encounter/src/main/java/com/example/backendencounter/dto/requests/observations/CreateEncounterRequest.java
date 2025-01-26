package com.example.backendencounter.dto.requests.observations;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateEncounterRequest {
    private int patientId;
    private int staffId;
    private Integer locationId;
}
