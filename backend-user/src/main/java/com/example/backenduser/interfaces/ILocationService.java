package com.example.backenduser.interfaces;

import com.example.backenduser.util.IResult;

public interface ILocationService {
  IResult getAll();
  IResult getById(int id);
}


