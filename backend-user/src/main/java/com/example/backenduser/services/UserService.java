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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public IResult registerUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return new Result<>(false,"A User already exists witht his email");
        }
        
        User user;
        var role = Role.fromId(request.getRoleId());
        if (role == Role.PATIENT){
            user = new Patient();
        }
        else if (role == Role.PRACTITIONER) {
            Practitioner practitioner = new Practitioner();
            Organization organization = organizationRepository.findById(request.getOrganizationId())
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));

            practitioner.setOrganization(organization);
            user = practitioner;
        } else if (role == Role.OTHER){
            Other other = new Other();

            Organization organization = organizationRepository.findById(request.getOrganizationId())
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            other.setOrganization(organization);
            user = other;
        }
        else{
            return new Result<>(false, "No Sutch Role Supported");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.fromId(request.getRoleId()));

        var result = userRepository.save(user);

        var response = Converter.convertUser(result);
        return new Result<UserResponse>(true,"",response);
    }

    public IResult authenticateUser(LoginRequest request) {
        var userOption = userRepository.findByEmail(request.getEmail());
        if( userOption.isEmpty()){
            return new Result(false,"Invalid mail or password, fail");
        }
        var user = userOption.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new Result(false,"Invalid email or password,fail");
        }
    
        var response = Converter.convertUser(user);
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

    public IResult getUserById(int id){
        var user = userRepository.findById(id);

        if(user.isEmpty()){
            return new Result<>(false,"Repository Issues");
        }

        var userResponse = Converter.convertUser(user.get());

        return new Result<UserResponse>(true,"",userResponse);
    }
}