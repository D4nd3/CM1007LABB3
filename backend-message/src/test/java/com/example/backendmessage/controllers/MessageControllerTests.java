package com.example.backendmessage.controllers;


import com.example.backendmessage.dto.requests.messages.*;
import com.example.backendmessage.interfaces.IMessageService;
import com.example.backendmessage.util.Result;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class MessageControllerTests {

    @Mock
    private IMessageService mockMessageService;

    private MessageController _sut;

    private EnhancedRandom enchancedRandom;

    // #region SetUp
    
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        _sut = new MessageController(mockMessageService);
        enchancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
    }

    // #endregion

    // #region Constructor 

    @Test
    void Constructor_Throws_Exception_When_MessageService_Is_Null() throws Exception {
        // Arrange

        // Act
        var exception = assertThrows(NullPointerException.class, 
            () -> new MessageController(null), 
            "Expected constructor to throw a NullPointerException when messageService is null"
        );

        // Assert
        assertEquals("messageService must not be null", exception.getMessage());
    }

    // #endregion

    // #region get

    @Test
    void get_Calls_MessageService_With_Request() {
        // Arrange
        var userId = enchancedRandom.nextObject(int.class);
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockMessageService.getMessagesByUserId(userId))
            .thenReturn(response);

        // Act
        _sut.get(userId);

        // Assert
        verify(mockMessageService, times(1)).getMessagesByUserId(userId);
    }

    @Test
    void get_Returns_BadRequest_When_ResultIsFailure() {
        // Arrange
        var userId = enchancedRandom.nextObject(int.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockMessageService.getMessagesByUserId(userId))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.get(userId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void get_Returns_message_from_MessageService_When_ResultIsFailure() {
        // Arrange
        var userId = enchancedRandom.nextObject(int.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockMessageService.getMessagesByUserId(userId))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.get(userId);

        // Assert
        assertEquals(message, response.getBody());
    }

    // #endregion

    // #region send

    @Test
    void send_Calls_MessageService_With_Request() {
        // Arrange
        var request = enchancedRandom.nextObject(SendMessageRequest.class);
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockMessageService.send(request))
            .thenReturn(response);

        // Act
        _sut.send(request);

        // Assert
        verify(mockMessageService, times(1)).send(request);
    }

    @Test
    void send_Returns_BadRequest_When_ResultIsFailure() {
        // Arrange
        var request = enchancedRandom.nextObject(SendMessageRequest.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockMessageService.send(request))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.send(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void send_Returns_message_from_MessageService_When_ResultIsFailure() {
        // Arrange
        var request = enchancedRandom.nextObject(SendMessageRequest.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockMessageService.send(request))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.send(request);

        // Assert
        assertEquals(message, response.getBody());
    }

    // #endregion

    // #region updateIsRead

    @Test
    void updateIsRead_Calls_MessageService_With_Request() {
        // Arrange
        var id = enchancedRandom.nextObject(int.class);
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockMessageService.updateIsRead(id))
            .thenReturn(response);

        // Act
        _sut.updateIsRead(id);

        // Assert
        verify(mockMessageService, times(1)).updateIsRead(id);
    }

    @Test
    void updateIsRead_Returns_BadRequest_When_ResultIsFailure() {
        // Arrange
        var id = enchancedRandom.nextObject(int.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockMessageService.updateIsRead(id))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.updateIsRead(id);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateIsRead_Returns_message_from_MessageService_When_ResultIsFailure() {
        // Arrange
        var id = enchancedRandom.nextObject(int.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockMessageService.updateIsRead(id))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.updateIsRead(id);

        // Assert
        assertEquals(message, response.getBody());
    }

    // #endregion
}