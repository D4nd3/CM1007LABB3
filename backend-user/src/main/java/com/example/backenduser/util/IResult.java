package com.example.backenduser.util;


public interface IResult {
    boolean isSuccess();
    String getMessage();
    Object getData();
}