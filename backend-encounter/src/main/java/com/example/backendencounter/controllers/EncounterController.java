package com.example.backendencounter.controllers;

import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backendencounter.dto.requests.observations.*;
import com.example.backendencounter.dto.responses.observations.*;
import com.example.backendencounter.interfaces.IEncounterService;
import com.example.backendencounter.util.Result;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/encounters")
public class EncounterController {

    private final IEncounterService encounterService;

    public EncounterController(IEncounterService encounterService) {
        this.encounterService = Objects.requireNonNull(encounterService, "encounterService must not be null");
    }

    @PostMapping("createEncounter")
    public ResponseEntity<?> CreateEncounter(@RequestBody CreateEncounterRequest createRequest, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        var result = encounterService.addEncounter(createRequest, token);

         if(!result.getSuccess()){
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(result.getMessage());
        }
        
        return ResponseEntity.ok(result.getData());
    }

    @PostMapping("createObservation")
    public ResponseEntity<?> CreateObservation(@RequestBody CreateObservationRequest request){
        var result = encounterService.addObservation(request);

         if(!result.getSuccess()){
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(result.getMessage());
        }
        
        return ResponseEntity.ok(result.getData());
    }

    @PostMapping("createCondition")
    public ResponseEntity<?> CreateCondition(@RequestBody CreateConditionRequest request){
        var result = encounterService.addCondition(request);

         if(!result.getSuccess()){
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(result.getMessage());
        }
        
        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("byStaffId")
    public ResponseEntity<?> GetByStaffId(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        var result = encounterService.getEncountersByStaffId(id, token);

        if(!result.getSuccess()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getMessage());
        }

        if (result instanceof Result<?> res && res.getData() instanceof List<?> data && data.stream().allMatch(item -> item instanceof EncounterResponse)) {
            return ResponseEntity.ok(data);
        }

        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Non expected return type");
    }

    @GetMapping("byPatientId")
    public ResponseEntity<?> GetByPatientId(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        var result = encounterService.getEncountersByPatientId(id, token);

        if(!result.getSuccess()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getMessage());
        }

        if (result instanceof Result<?> res && res.getData() instanceof List<?> data && data.stream().allMatch(item -> item instanceof EncounterResponse)) {
            return ResponseEntity.ok(data);
        }

        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Non expected return type");
    }
}