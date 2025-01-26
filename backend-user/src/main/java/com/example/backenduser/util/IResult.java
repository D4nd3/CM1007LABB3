package com.example.backenduser.util;


public interface IResult {
    boolean getSuccess();
    String getMessage();
    Object getData();
}