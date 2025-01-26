package com.example.search.services;

import java.util.List;
import java.util.stream.Collectors;

import com.example.search.dto.requests.PractitionerAndDateRequest;
import com.example.search.dto.responses.UserResponse;
import com.example.search.interfaces.IPractitionerService;
import com.example.search.models.Patient;
import com.example.search.repositories.*;
import com.example.search.util.Converter;
import com.example.search.util.IResult;
import com.example.search.util.Result;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PractitionerService implements IPractitionerService {

    @Inject
    PractitionerRepository practitionerRepository;

    public IResult PatientsByPractitionerAndDay(PractitionerAndDateRequest request){
      var encounters = practitionerRepository.findEncountersByPractitionerAndDate(request.getPractitionerId(), request.getDate());
      if (encounters == null){
        return new Result<>(false,"Repository Issues");
      } 

      List<Patient> patients = encounters.stream()
        .map(encounter -> encounter.getPatient())
        .distinct()
        .collect(Collectors.toList());

      var userResponses = patients.stream().map(Converter::convertUser).collect(Collectors.toList());
      
      return new Result<List<UserResponse>>(true,"",userResponses);
    }

    public IResult PatientsByPractitioner(int id){
      var patients = practitionerRepository.findPatientsByPractitioner(id);
      if (patients == null){
        return new Result<>(false,"Repository Issues");
      } 

      var userResponses = patients.stream().map(Converter::convertUser).collect(Collectors.toList());
      
      return new Result<List<UserResponse>>(true,"",userResponses);
    }
}