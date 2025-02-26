package com.example.backendencounter.interfaces;

import com.example.backendencounter.dto.requests.observations.*;
import com.example.backendencounter.util.IResult;

public interface IEncounterService {
   
  IResult addCondition(CreateConditionRequest request);

  IResult addObservation(CreateObservationRequest request);

  IResult addEncounter(CreateEncounterRequest request, String token);

  IResult getEncountersByStaffId(String id, String token);

  IResult getEncountersByPatientId(String id, String token);
}
