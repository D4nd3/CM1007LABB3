package com.example.backendmessage.dto.requests.messages;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendMessageRequest {
    private int senderId;
    private int receiverId;
    private String text;
}
