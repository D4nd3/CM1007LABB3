package com.example.backendnote.dto.requests.notes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateRequest {
    private int patientId;
    private int staffId;
    private String text;
}
