package com.example.backenduser.controllers;

import com.example.backenduser.dto.requests.users.*;
import com.example.backenduser.dto.responses.users.*;
import com.example.backenduser.interfaces.IUserService;
import com.example.backenduser.util.Result;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserRequest request) {
        var result = userService.registerUser(request);

        if(!result.getSuccess()){
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(result.getMessage());
        }
        if (result instanceof Result<?> res && res.getData() instanceof UserResponse userResponse) {
            
            return ResponseEntity.ok(userResponse);
        }
        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Non expected return type");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        var result = userService.authenticateUser(loginRequest);
            
        if(!result.getSuccess()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result.getMessage());
        }
        if (result instanceof Result<?> res && res.getData() instanceof UserResponse authenticatedUser) {
            
            return ResponseEntity.ok(authenticatedUser);
        }
        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Non expected return type");
    }

    @GetMapping("/allStaff")
    public ResponseEntity<?> getAllStaff(){
        var result = userService.getAllStaff();

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

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        var result = userService.getAll();

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

    @GetMapping("/byId")
    public ResponseEntity<?> getById(@RequestParam int id){
        var result = userService.getUserById(id);
            
        if(!result.getSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getMessage());
        }
        if (result instanceof Result<?> res && res.getData() instanceof UserResponse userResponse) {
            
            return ResponseEntity.ok(userResponse);
        }
        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Non expected return type");
        
    }

}