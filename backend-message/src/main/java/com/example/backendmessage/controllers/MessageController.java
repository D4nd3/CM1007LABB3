package com.example.backendmessage.controllers;

import java.util.Objects;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.backendmessage.dto.requests.messages.*;
import com.example.backendmessage.interfaces.IMessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService, "messageService must not be null");
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam int userId) {
        var result = messageService.getMessagesByUserId(userId);
        
        if(!result.getSuccess()){
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(result.getMessage());
        }
        
        return ResponseEntity.ok(result.getData());
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody SendMessageRequest request) {
        var result = messageService.send(request);

        if(!result.getSuccess()){
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(result.getMessage());
        }

        return ResponseEntity.ok(result.getData());
    }

    @PutMapping("/updateIsRead")
    public ResponseEntity<?> updateIsRead(@RequestParam int id) {
        var result = messageService.updateIsRead(id);
        
        if (!result.getSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getMessage());
        }

        return ResponseEntity.ok(result.getData());
    }
}
