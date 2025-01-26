package com.example.search.interfaces;

import com.example.search.dto.requests.PractitionerAndDateRequest;
import com.example.search.util.IResult;

public interface IPractitionerService {
   
  IResult PatientsByPractitionerAndDay(PractitionerAndDateRequest request);
  IResult PatientsByPractitioner(int id);
}
