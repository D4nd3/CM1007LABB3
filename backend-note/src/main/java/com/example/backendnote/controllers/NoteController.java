package com.example.backendnote.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendnote.dto.requests.notes.CreateRequest;
import com.example.backendnote.dto.responses.notes.NoteResponse;
import com.example.backendnote.interfaces.INoteService;
import com.example.backendnote.util.Result;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final INoteService noteService;

    public NoteController(INoteService noteService) {
        this.noteService = Objects.requireNonNull(noteService, "noteService must not be null");
    }

    @GetMapping("/byPatientId")
    public ResponseEntity<?> getByPatientId(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("Authorization");

        var result = noteService.getByPatientId(id, token);

        if(!result.getSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getMessage());
        }

        if (result instanceof Result<?> res && res.getData() instanceof List<?> data && data.stream().allMatch(item -> item instanceof NoteResponse)) {
            return ResponseEntity.ok(data);
        }

        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Non expected return type");
    }

    @GetMapping("/byStaffId")
    public ResponseEntity<?> getByStaffId(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("Authorization");

        var result = noteService.getByStaffId(id,token);

        if(!result.getSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getMessage());
        }

        if (result instanceof Result<?> res && res.getData() instanceof List<?> data && data.stream().allMatch(item -> item instanceof NoteResponse)) {
            return ResponseEntity.ok(data);
        }

        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Non expected return type");
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateRequest createRequest, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        var result = noteService.create(createRequest,token);

        if(!result.getSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getMessage());
        }

        if (result instanceof Result<?> res && res.getData() instanceof NoteResponse noteResponse) {
            return ResponseEntity.ok(noteResponse);
        }

        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Non expected return type");
    }
}
