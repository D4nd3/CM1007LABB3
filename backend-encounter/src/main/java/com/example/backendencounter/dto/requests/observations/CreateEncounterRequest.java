package com.example.backendencounter.dto.requests.observations;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateEncounterRequest {
    private String patientId;
    private String staffId;
    private Integer locationId;
}
