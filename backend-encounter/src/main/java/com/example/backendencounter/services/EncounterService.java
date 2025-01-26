package com.example.backendencounter.services;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendencounter.dto.requests.observations.*;
import com.example.backendencounter.dto.responses.observations.*;
import com.example.backendencounter.dto.responses.users.UserResponse;
import com.example.backendencounter.enums.Role;
import com.example.backendencounter.interfaces.IEncounterService;
import com.example.backendencounter.models.*;
import com.example.backendencounter.proxies.UserServiceProxy;
import com.example.backendencounter.repositories.*;
import com.example.backendencounter.util.*;

@Service
public class EncounterService implements IEncounterService{
    @Autowired
    ObservationRepository observationRepository;

    @Autowired
    EncounterRepository encounterRepository;

    @Autowired
    ConditionRepository conditionRepository;

    @Autowired
    private UserServiceProxy userServiceProxy;


    public IResult addCondition(CreateConditionRequest request){
        var observation = observationRepository.findById(request.getObservationId());

        if(observation.isEmpty()){
            return new Result<>(false,"Obervation coudln't be found");
        }
        
        var condition = new Condition();
        condition.setDescription(request.getDescription());
        condition.setName(request.getName());
        condition.setObservation(observation.get());

        condition = conditionRepository.save(condition);

        var result = Converter.convertCondition(condition);

        return new Result<ConditionResponse>(true,"",result);
    }

    public IResult addObservation(CreateObservationRequest request){
        var encounter = encounterRepository.findById(request.getEncounterId());

        if(encounter.isEmpty()){
            return new Result<>(false,"Encounter coudln't be found");
        }
        
        var observation = new Observation();
        observation.setDescription(request.getDescription());
        observation.setEncounter(encounter.get());
        observation.setDescription(request.getDescription());
        observation.setCreated(Date.from(Instant.now()));
        observation.setVisitDate(request.getVisitDate());
        observation = observationRepository.save(observation);


        var result = Converter.convertObservation(observation);
        return new Result<ObservationResponse>(true,"",result);
    }

    public IResult addEncounter(CreateEncounterRequest request){
        var staff = userServiceProxy.findUserById(request.getStaffId());
        if(staff == null || !(staff.getRole() == Role.PRACTITIONER || staff.getRole() == Role.OTHER)){
            return new Result<>(false, "Couldn't find staff member");
        }

        var patient = userServiceProxy.findUserById(request.getPatientId());
        if(patient == null || patient.getRole() != Role.PATIENT){
            return new Result<>(false, "Couldnt find patient");
        }

        var encounter = new Encounter();
       
        encounter.setPatient_id(request.getPatientId());
        encounter.setStaff_id(request.getStaffId());
        encounter.setLocation_id(request.getLocationId());

        encounter = encounterRepository.save(encounter);

        var result = Converter.convertEncounter(encounter);
        result.setPatient(patient);
        result.setStaff(staff);

        if(request.getLocationId() != null){
            var location = userServiceProxy.findLocationById(request.getLocationId());
            if(location == null){
                return new Result<>(false, "Couldn't find Location");
            }
            result.setLocation(location);
        }
       
        return new Result<EncounterResponse>(true,"",result);
    }

    public IResult getEncountersByStaffId(int id){

        var staff = userServiceProxy.findUserById(id);
        if(staff == null || !(staff.getRole() == Role.PRACTITIONER || staff.getRole() == Role.OTHER)){
            return new Result<>(false, "Couldn't find staff member");
        }

        var encounters = encounterRepository.findByStaffId(id);

        if(encounters == null){
            return new Result<>(false,"Repository Issues");
        }

        var uniquePatientIds = encounters.stream()
            .map(Encounter::getPatient_id)
            .distinct()
            .collect(Collectors.toList());

        var patientResponses = uniquePatientIds.stream()
            .map(userServiceProxy::findUserById)
            .filter(java.util.Objects::nonNull)
            .collect(Collectors.toMap(UserResponse::getId, user -> user));  

        var result = encounters.stream().map(encounter -> {
            var patient = patientResponses.get(encounter.getPatient_id());
            var location = encounter.getLocation_id() != null ? userServiceProxy.findLocationById(encounter.getLocation_id()) : null;
            var observations = encounter.getObservations().stream().map(Converter::convertObservation).collect(Collectors.toList());
            return new EncounterResponse(
                encounter.getId(),
                patient,
                staff,
                location,
                observations
            );
        }).collect(Collectors.toList());

        return new Result<List<EncounterResponse>>(true,"",result);
    }

    public IResult getEncountersByPatientId(int id){
        var patient = userServiceProxy.findUserById(id);
        if(patient == null || patient.getRole() != Role.PATIENT){
            return new Result<>(false, "Couldn't find staff member");
        }
        var encounters = encounterRepository.findByPatientId(id);

        if(encounters == null){
            return new Result<>(false,"Repository Issues");
        }

        var uniqueStaffIds = encounters.stream()
            .map(Encounter::getStaff_id)
            .distinct()
            .collect(Collectors.toList());

        var staffResponses = uniqueStaffIds.stream()
            .map(userServiceProxy::findUserById)
            .filter(java.util.Objects::nonNull)
            .collect(Collectors.toMap(UserResponse::getId, user -> user));  


        var result = encounters.stream().map(encounter -> {
            var staff = staffResponses.get(encounter.getStaff_id()); 
            var location = encounter.getLocation_id() != null ? userServiceProxy.findLocationById(encounter.getLocation_id()) : null;
            var observations = encounter.getObservations().stream().map(Converter::convertObservation).collect(Collectors.toList());
            return new EncounterResponse(
                encounter.getId(),
                patient,
                staff,
                location,
                observations
            );
        }).collect(Collectors.toList());
        return new Result<List<EncounterResponse>>(true,"",result);
    }
}
