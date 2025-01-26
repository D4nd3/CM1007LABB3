package com.example.backendnote.interfaces;

import com.example.backendnote.util.IResult;
import com.example.backendnote.dto.requests.notes.CreateRequest;

public interface INoteService {
  IResult getByPatientId(int id);
  IResult getByStaffId(int staffId);
  IResult create(CreateRequest request);
}
