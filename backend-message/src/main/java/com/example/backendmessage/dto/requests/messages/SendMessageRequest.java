package com.example.backendmessage.dto.requests.messages;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendMessageRequest {
    private String senderId;
    private String receiverId;
    private String text;
}
