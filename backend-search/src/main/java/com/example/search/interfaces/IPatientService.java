package com.example.search.interfaces;

import com.example.search.util.IResult;

public interface IPatientService {
   
  IResult findPatientsByName(String name);

  IResult findPatientsByCondition(String name);

  IResult findPatientsByStaffName(String name);
}
