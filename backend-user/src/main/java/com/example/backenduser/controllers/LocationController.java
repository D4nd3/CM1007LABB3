package com.example.backenduser.controllers;

import com.example.backenduser.dto.responses.users.*;
import com.example.backenduser.interfaces.ILocationService;
import com.example.backenduser.util.Result;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
public class LocationController {


    private final ILocationService locationService;

    public LocationController(ILocationService locationService) {
        this.locationService = Objects.requireNonNull(locationService, "locationService must not be null");
    }

    @GetMapping()
    public ResponseEntity<?> get(){
        var result = locationService.getAll();
            
        if(!result.isSuccess()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getMessage());
        }
        if (result instanceof Result<?> res && res.getData() instanceof List<?> data && data.stream().allMatch(item -> item instanceof LocationResponse)) {
            
            return ResponseEntity.ok(data);
        }
        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Non expected return type");
        
    }

    @GetMapping("byId")
    public ResponseEntity<?> getById(@RequestParam int id){
        var result = locationService.getById(id);
            
        if(!result.isSuccess()){
            return ResponseEntity.status(401).body(result.getMessage());
        }
        if (result instanceof Result<?> res && res.getData() instanceof LocationResponse locationResponse) {
            
            return ResponseEntity.ok(locationResponse);
        }
        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Non expected return type");
        
    }

}