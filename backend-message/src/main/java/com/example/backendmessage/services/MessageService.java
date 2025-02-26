package com.example.backendmessage.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendmessage.dto.requests.messages.SendMessageRequest;
import com.example.backendmessage.dto.responses.messages.MessageResponse;
import com.example.backendmessage.dto.responses.users.UserResponse;
import com.example.backendmessage.interfaces.IMessageService;
import com.example.backendmessage.models.Message;
import com.example.backendmessage.proxies.UserServiceProxy;
import com.example.backendmessage.repositories.MessageRepository;
import com.example.backendmessage.util.IResult;
import com.example.backendmessage.util.Result;

@Service
public class MessageService implements IMessageService{
    
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserServiceProxy userServiceProxy;

    public IResult getMessagesByUserId(String id, String token) {
        var userResponse = userServiceProxy.findUserById(id, token);
        if (userResponse == null) {
            return new Result<>(false, "Kunde inte hitta användaren i UserService.");
        }

        List<Message> sentMessages = messageRepository.findBySenderId(id);

        var uniqueReceiverIds = sentMessages.stream()
            .map(Message::getReceiver_user_id)
            .distinct()
            .collect(Collectors.toList());

        var receiverResponses = uniqueReceiverIds.stream()
        .map(receiverId -> userServiceProxy.findUserById(receiverId, token))
        .filter(java.util.Objects::nonNull)
        .collect(Collectors.toMap(UserResponse::getId, user -> user));

        var sentResult = sentMessages.stream().map(x->{
            var receiverResponse = receiverResponses.get(x.getReceiver_user_id());
            return new MessageResponse(
                x.getId(),
                x.getText(),
                x.getSender_user_id(),
                userResponse.getFullName(),
                x.getReceiver_user_id(),
                receiverResponse.getFullName(),
                x.getTimestamp(),
                x.getIsRead()
            );
        }).collect(Collectors.toList());

        List<Message> receivedMessages = messageRepository.findByReceiverId(id);

        var uniqueSenderIds = receivedMessages.stream()
            .map(Message::getSender_user_id)
            .distinct()
            .collect(Collectors.toList());

        var senderResponses = uniqueSenderIds.stream()
            .map(receiverId -> userServiceProxy.findUserById(receiverId, token))
            .filter(java.util.Objects::nonNull)
            .collect(Collectors.toMap(UserResponse::getId, user -> user));

        var receivedResult = receivedMessages.stream().map(x->{
            var senderResponse = senderResponses.get(x.getSender_user_id());
            return new MessageResponse(
                x.getId(),
                x.getText(),
                x.getSender_user_id(),
                senderResponse.getFullName(),
                x.getReceiver_user_id(),
                userResponse.getFullName(),
                x.getTimestamp(),
                x.getIsRead()
            );
        }).collect(Collectors.toList());
        
        List<MessageResponse> result = new ArrayList<>();
        result.addAll(sentResult);
        result.addAll(receivedResult);

        return new Result<List<MessageResponse>>(true,"",result);
    }

    public IResult send(SendMessageRequest request, String token) {
        var sender = userServiceProxy.findUserById(request.getSenderId(), token);

        if (sender == null) {
            return new Result<>(false, "Avsändaren kunde inte hittas.");
        }

        var receiver =  userServiceProxy.findUserById(request.getReceiverId(), token);
        if (receiver == null) {
            return new Result<>(false, "Mottagaren kunde inte hittas.");
        }

        var message = new Message();
        message.setSender_user_id(request.getSenderId());
        message.setReceiver_user_id(request.getReceiverId());
        message.setText(request.getText());
        message.setIsRead(false);
        message.setTimestamp(System.currentTimeMillis());

        message = messageRepository.save(message);

        var value = new MessageResponse(message.getId(),message.getText(),message.getSender_user_id(),sender.getFullName(),
            message.getReceiver_user_id(),receiver.getFullName(),
            message.getTimestamp(),message.getIsRead());

        return new Result<MessageResponse>(true,"",value);
    }

    public IResult updateIsRead(int messageId, String token) {
        var optionalMessage = messageRepository.findById(messageId);
        
        if (optionalMessage.isEmpty()) {
            return new Result<>(false, "Meddelandet kunde inte hittas.");
        }

        var message = optionalMessage.get();

        var sender = userServiceProxy.findUserById(message.getSender_user_id(), token);

        if (sender == null) {
            return new Result<>(false, "Avsändaren kunde inte hittas.");
        }

        var receiver =  userServiceProxy.findUserById(message.getReceiver_user_id(), token);
        if (receiver == null) {
            return new Result<>(false, "Mottagaren kunde inte hittas.");
        }

        message.setIsRead(true);
        message = messageRepository.save(message);

        var value = new MessageResponse(message.getId(),message.getText(),message.getSender_user_id(),sender.getFullName(),
            message.getReceiver_user_id(),receiver.getFullName(),
            message.getTimestamp(),message.getIsRead());

        return new Result<MessageResponse>(true, "",value);
    }
}
