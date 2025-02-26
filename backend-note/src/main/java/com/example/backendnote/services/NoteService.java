package com.example.backendnote.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendnote.dto.requests.notes.CreateRequest;
import com.example.backendnote.dto.responses.notes.NoteResponse;
import com.example.backendnote.dto.responses.users.UserResponse;
import com.example.backendnote.enums.Role;
import com.example.backendnote.interfaces.INoteService;
import com.example.backendnote.models.*;
import com.example.backendnote.proxies.UserServiceProxy;
import com.example.backendnote.repositories.*;
import com.example.backendnote.util.*;

@Service
public class NoteService implements INoteService{

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserServiceProxy userServiceProxy;

    public IResult getByPatientId(String id, String token){
        var patientResponse = userServiceProxy.findUserById(id, token);
        if (patientResponse == null || patientResponse.getRole() != Role.PATIENT) {
            return new Result<>(false, "Kunde inte hitta patienten i UserService.");
        }

        var notes = noteRepository.findByPatientId(id);

        if (notes == null || notes.isEmpty()) {
            return new Result<List<NoteResponse>>(true, "", new ArrayList<NoteResponse>());
        }


        var uniqueStaffIds = notes.stream()
            .map(Note::getStaff_id)
            .distinct()
            .collect(Collectors.toList());

        var staffResponses = uniqueStaffIds.stream()
        .map(uniqueId -> userServiceProxy.findUserById(uniqueId, token))
        .filter(java.util.Objects::nonNull)
        .collect(Collectors.toMap(UserResponse::getId, user -> user));

        var result = notes.stream().map(note -> {
            var staffResponse = staffResponses.get(note.getStaff_id());
            return new NoteResponse(
                note.getId(),
                note.getText(),
                patientResponse.getFullName(),
                staffResponse != null ? staffResponse.getFullName() : "Okänd Personal"
            );
        }).collect(Collectors.toList());

        return new Result<List<NoteResponse>>(true,"",result);
    }

    public IResult getByStaffId(String staffId, String token) {
        var staffResponse = userServiceProxy.findUserById(staffId, token);
        if (staffResponse == null || staffResponse.getRole() != Role.OTHER || staffResponse.getRole() != Role.PRACTITIONER) {
            return new Result<>(false, "Kunde inte hitta personalen i UserService.");
        }

        var notes = noteRepository.findByStaffId(staffId);
    
        if (notes == null || notes.isEmpty()) {
            return new Result<List<NoteResponse>>(true, "", new ArrayList<NoteResponse>());
        }
    
        var uniquePatientIds = notes.stream()
            .map(Note::getPatient_id)
            .distinct()
            .collect(Collectors.toList());
    
        var patientResponses = uniquePatientIds.stream()
            .map(uniqueId ->userServiceProxy.findUserById(uniqueId, token))
            .filter(java.util.Objects::nonNull)
            .collect(Collectors.toMap(UserResponse::getId, user -> user));
    
        var result = notes.stream().map(note -> {
            var patientResponse = patientResponses.get(note.getPatient_id());
            return new NoteResponse(
                note.getId(),
                note.getText(),
                patientResponse != null ? patientResponse.getFullName() : "Okänd Patient",
                staffResponse.getFullName()
            );
        }).collect(Collectors.toList());
    
        return new Result<List<NoteResponse>>(true, "", result);
    }

    public IResult create(CreateRequest request, String token) {
        var patientResponse = userServiceProxy.findUserById(request.getPatientId(), token);
        if (patientResponse == null || patientResponse.getRole() != Role.PATIENT) {
            return new Result<>(false, "Patienten kunde inte hämtas.");
        }

        var staffResponse = userServiceProxy.findUserById(request.getStaffId(), token);
        if(staffResponse == null || !(staffResponse.getRole() == Role.OTHER || staffResponse.getRole() == Role.PRACTITIONER)) {
            return new Result<>(false, "Personal kunde inte hämtas.");
        }
        var note = new Note();
        note.setPatient_id(patientResponse.getId());
        note.setStaff_id(staffResponse.getId());
        note.setText(request.getText());
        note = noteRepository.save(note);
        var result = new NoteResponse(note.getId(), note.getText(), patientResponse.getFullName(),staffResponse.getFullName());

        return new Result<NoteResponse>(true,"",result);
    }
    
}