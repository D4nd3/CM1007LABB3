package com.example.backendencounter.util;

import java.util.List;
import java.util.stream.Collectors;

import com.example.backendencounter.dto.responses.observations.*;
import com.example.backendencounter.dto.responses.users.*;
import com.example.backendencounter.models.*;

public class Converter {

    public static EncounterResponse convertEncounter(Encounter encounter) {
        return new EncounterResponse(
            encounter.getId(),
            null,
            null,
            null,
            encounter.getObservations()
                .stream()
                .map(Converter::convertObservation)
                .collect(Collectors.toList())
        );
    }

    public static ObservationResponse convertObservation(Observation observation) {
        return new ObservationResponse(
            observation.getId(),
            observation.getDescription(),
            observation.getCreated(),
            observation.getVisitDate(),
            observation.getConditions()
                .stream()
                .map(Converter::convertCondition)
                .collect(Collectors.toList())
        );
    }

    public static ConditionResponse convertCondition(Condition condition) {
        return new ConditionResponse(
            condition.getId(),
            condition.getName(),
            condition.getDescription()
        );
    }
}