package com.example.backendmessage.interfaces;

import com.example.backendmessage.dto.requests.messages.SendMessageRequest;
import com.example.backendmessage.util.IResult;

public interface IMessageService {
   
  IResult getMessagesByUserId(int id);

  IResult send(SendMessageRequest request);
    
  IResult updateIsRead(int messageId);
}
