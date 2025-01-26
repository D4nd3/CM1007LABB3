package com.example.backenduser.services;

import com.example.backenduser.dto.responses.organizations.OrganizationResponse;
import com.example.backenduser.interfaces.IOrganizationService;
import com.example.backenduser.models.*;
import com.example.backenduser.repositories.OrganizationRepository;
import com.example.backenduser.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationService implements IOrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public IResult getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();

        var response = organizations.stream()
                .map(org -> new OrganizationResponse(
                        org.getId(),
                        org.getName(),
                        org.getAddress(),
                        org.getPhoneNumber(),
                        org.getEmail()
                ))
                .collect(Collectors.toList());

        return new Result<List<OrganizationResponse>>(true,"",response);
    }
}