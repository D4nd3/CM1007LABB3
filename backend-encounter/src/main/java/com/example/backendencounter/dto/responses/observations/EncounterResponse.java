package com.example.backendencounter.dto.responses.observations;

import java.util.List;

import com.example.backendencounter.dto.responses.users.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class EncounterResponse {

    private int id;
    
    private UserResponse patient;
    
    private UserResponse staff;
    
    private LocationResponse location;
    
    private List<ObservationResponse> observations;
}