package com.example.backenduser.controllers;

import com.example.backenduser.interfaces.IOrganizationService;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final IOrganizationService organizationService;

    public OrganizationController(IOrganizationService organizationService) {
        this.organizationService = Objects.requireNonNull(organizationService, "organizationService must not be null");
    }

    @GetMapping
    public ResponseEntity<?> getAllOrganizations() {
        var result = organizationService.getAllOrganizations();
        
        if(!result.getSuccess()){
            return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(result.getMessage());
        }
        
        return ResponseEntity.ok(result.getData());
    }
}