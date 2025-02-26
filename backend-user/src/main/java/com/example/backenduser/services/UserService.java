package com.example.backenduser.services;

import com.example.backenduser.models.*;
import com.example.backenduser.repositories.*;
import com.example.backenduser.util.*;
import com.example.backenduser.dto.requests.users.*;
import com.example.backenduser.dto.responses.users.UserResponse;
import com.example.backenduser.enums.Role;
import com.example.backenduser.interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private KeycloakAdminService keycloakAdminService;


    public IResult registerUser(CreateUserRequest request) {
        if (request.getRoleId() != 1 && !KeycloakAdminService.hasRole("Admin")) {
            return new Result<>(false, "Authorization failed");
        }

        if (keycloakAdminService.isUserExists(request.getEmail(), request.getUsername())) {
            return new Result<>(false,"A User already exists with this email");
        }
        
        User user;
        String groupName;
        IResult keycloackResult;
        var role = Role.fromId(request.getRoleId());
        if (role == Role.PATIENT){
            groupName = "PATIENT";
            user = new Patient();
        }
        else if (role == Role.PRACTITIONER) {
            Practitioner practitioner = new Practitioner();
            groupName = "PRACTITIONER";
            Organization organization = organizationRepository.findById(request.getOrganizationId())
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));

            practitioner.setOrganization(organization);
            user = practitioner;
        } else if (role == Role.OTHER){
            Other other = new Other();
            groupName = "OTHER";
            Organization organization = organizationRepository.findById(request.getOrganizationId())
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            other.setOrganization(organization);
            user = other;
        }
        else{
            return new Result<>(false, "No Sutch Role Supported");
        }

        keycloackResult = keycloakAdminService.createUserWithGroup(request, groupName);

        if (!(keycloackResult.isSuccess() && keycloackResult instanceof Result<?> res && res.getData() instanceof String userId)) {
            
            return new Result<>(false, "Keycloak user creation failed: " + keycloackResult.getMessage());
        }
        user.setId(userId); 
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(role);
        var result = userRepository.save(user);

        var response = Converter.convertUser(result);
        return new Result<UserResponse>(true,"",response);
    }

    public IResult getAllStaff(){
        List<Role> roles = new ArrayList<>();
        roles.add(Role.PRACTITIONER);
        roles.add(Role.OTHER);
        var staffs = userRepository.findByRoleIn(roles);

        if (staffs == null){
            return new Result<>(false,"Repository Issues");
        }

        var staffResponses = staffs.stream().map(Converter::convertUser).collect(Collectors.toList());

        return new Result<List<UserResponse>>(true,"",staffResponses);
    }

    public IResult getAll(){
        var users = userRepository.findAll();

        if (users == null){
            return new Result<>(false,"Repository Issues");
        }

        var userResponses = users.stream().map(Converter::convertUser).collect(Collectors.toList());

        return new Result<List<UserResponse>>(true,"",userResponses);
    }

    public IResult getUserById(String id){
        var user = userRepository.findById(id);

        if(user.isEmpty()){
            return new Result<>(false,"Repository Issues");
        }

        var userResponse = Converter.convertUser(user.get());

        return new Result<UserResponse>(true,"",userResponse);
    }
}