package com.example.backendnote.util;


public interface IResult {
    boolean getSuccess();
    String getMessage();
    Object getData();
}