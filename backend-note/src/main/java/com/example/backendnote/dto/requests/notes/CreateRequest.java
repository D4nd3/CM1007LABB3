package com.example.backendnote.dto.requests.notes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateRequest {
    private String patientId;
    private String staffId;
    private String text;
}
