package com.example.backendencounter.dto.responses.observations;


import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ObservationResponse {
    
    private int id;
    private String description;
    private Date created;
    private Date visitDate;
    private List<ConditionResponse> conditions;
}
