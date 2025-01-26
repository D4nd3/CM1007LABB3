package com.example.backendencounter.dto.requests.observations;



import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateObservationRequest {
    private int encounterId;
    private String description;
    private Date visitDate;
}
