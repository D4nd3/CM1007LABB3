package com.example.backendencounter.util;


public interface IResult {
    boolean getSuccess();
    String getMessage();
    Object getData();
}