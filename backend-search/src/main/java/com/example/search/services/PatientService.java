package com.example.search.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.search.dto.responses.UserResponse;
import com.example.search.interfaces.IPatientService;
import com.example.search.models.Condition;
import com.example.search.repositories.*;
import com.example.search.util.Converter;
import com.example.search.util.IResult;
import com.example.search.util.Result;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PatientService implements IPatientService {

    @Inject
    PatientRepository patientRepository;

    @Inject
    ConditionRepository conditionRepository;

    @Inject
    StaffRepository staffRepository;

    public IResult findPatientsByName(String name) {
        var patients = patientRepository.findByName(name);
        if (patients == null){
          return new Result<>(false,"Repository Issues");
        } 
        var userResponses = patients.stream().map(Converter::convertUser).collect(Collectors.toList());
        return new Result<List<UserResponse>>(true,"",userResponses);
    }

    public IResult findPatientsByCondition(String name) {
      var conditionsByName = conditionRepository.findByName(name);
      var conditionsByDescription = conditionRepository.findByDescription(name);
    
      if(conditionsByName == null || conditionsByDescription == null){
        return new Result<>(false,"Repository Issues");
      }

      conditionsByName.addAll(conditionsByDescription);
      if(conditionsByName.isEmpty()){
        return new Result<List<UserResponse>>(true,"No Patients Found",new ArrayList<UserResponse>());
      }
      List<Condition> uniqueConditions = conditionsByName.stream()
        .distinct()
        .collect(Collectors.toList());

      var observations = uniqueConditions.stream()
        .map(condition -> condition.getObservation())
        .distinct()
        .collect(Collectors.toList());

      var encounters = observations.stream()
        .map(observation -> observation.getEncounter())
        .distinct()
        .collect(Collectors.toList());

      var patients = encounters.stream()
        .map(encounter -> encounter.getPatient())
        .distinct()
        .collect(Collectors.toList());

      var userResponses = patients.stream().map(Converter::convertUser).collect(Collectors.toList());

      return new Result<List<UserResponse>>(true,"",userResponses);
    }

    public IResult findPatientsByStaffName(String name) {
      var staffs = staffRepository.findByName(name);
      if (staffs == null){
        return new Result<>(false,"Repository Issues");
      } else if(staffs.isEmpty()){
        return new Result<List<UserResponse>>(false,"No Staff Found",new ArrayList<UserResponse>());
      }
      var encounters = staffs.stream()
        .map(staffMember -> staffMember.getEncounters())
        .flatMap(List::stream)
        .distinct()
        .collect(Collectors.toList());

      if(encounters.isEmpty()){
        return new Result<List<UserResponse>>(false,"No Encounters Found",new ArrayList<UserResponse>());
      }

      var patients = encounters.stream()
        .map(encounter -> encounter.getPatient())
        .distinct()
        .collect(Collectors.toList());

      var userResponses = patients.stream().map(Converter::convertUser).collect(Collectors.toList());

      return new Result<List<UserResponse>>(true,"",userResponses);
    }
}