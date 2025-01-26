package com.example.backendnote.dto.responses.notes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NoteResponse {
    private int id;
    private String text;
    private String patientName;
    private String staffName;
}
