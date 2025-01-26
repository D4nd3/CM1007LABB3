package com.example.search.dto.requests;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PractitionerAndDateRequest {
  private int practitionerId;
  private Date date;
}


