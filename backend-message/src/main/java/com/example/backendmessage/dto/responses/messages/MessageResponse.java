package com.example.backendmessage.dto.responses.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MessageResponse {
    private int id;

    private String text;

    private int senderId;

    private String senderName;

    private int receiverId;

    private String receiverName;

    private Long timestamp;

    private Boolean isRead;
}
