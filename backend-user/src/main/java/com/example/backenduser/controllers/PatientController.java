package com.example.backenduser.controllers;

import com.example.backenduser.dto.responses.users.*;
import com.example.backenduser.interfaces.IPatientService;
import com.example.backenduser.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = Objects.requireNonNull(patientService, "patientService must not be null");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPatients(){
        var result = patientService.getAll();

        if(!result.getSuccess()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getMessage());
        }

        if (result instanceof Result<?> res && res.getData() instanceof List<?> data && data.stream().allMatch(item -> item instanceof UserResponse)) {
            return ResponseEntity.ok(data);
        }

        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Non expected return type");
    }
}