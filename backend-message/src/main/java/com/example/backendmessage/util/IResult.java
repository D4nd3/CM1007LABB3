package com.example.backendmessage.util;


public interface IResult {
    boolean getSuccess();
    String getMessage();
    Object getData();
}