package com.example.backenduser.services;


import com.example.backenduser.dto.responses.users.*;
import com.example.backenduser.interfaces.IPatientService;
import com.example.backenduser.repositories.*;
import com.example.backenduser.util.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    public IResult getAll(){
        var users = patientRepository.findAll();

        var result = users.stream().map(Converter::convertUser).collect(Collectors.toList());

        return new Result<List<UserResponse>>(true,"",result);
    }
}