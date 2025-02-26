package com.example.backendnote.interfaces;

import com.example.backendnote.util.IResult;
import com.example.backendnote.dto.requests.notes.CreateRequest;

public interface INoteService {
  IResult getByPatientId(String id, String token);
  IResult getByStaffId(String staffId, String token);
  IResult create(CreateRequest request, String token);
}
