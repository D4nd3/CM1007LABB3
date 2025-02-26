package com.example.backendmessage.interfaces;

import com.example.backendmessage.dto.requests.messages.SendMessageRequest;
import com.example.backendmessage.util.IResult;

public interface IMessageService {
   
  IResult getMessagesByUserId(String id, String token);

  IResult send(SendMessageRequest request, String token);
    
  IResult updateIsRead(int messageId, String token);
}
