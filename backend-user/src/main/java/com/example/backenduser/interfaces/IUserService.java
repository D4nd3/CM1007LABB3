package com.example.backenduser.interfaces;

import com.example.backenduser.dto.requests.users.CreateUserRequest;
import com.example.backenduser.dto.requests.users.LoginRequest;
import com.example.backenduser.util.IResult;

public interface IUserService {
    IResult registerUser(CreateUserRequest request);
    IResult authenticateUser(LoginRequest request);
    IResult getAllStaff();
    IResult getAll();
    IResult getUserById(int id);
}