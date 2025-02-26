package com.example.backendnote.controllers;


import com.example.backendnote.dto.requests.notes.*;
import com.example.backendnote.interfaces.INoteService;
import com.example.backendnote.util.Result;

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
class NoteControllerTests {

    @Mock
    private INoteService mockNoteService;

    private NoteController _sut;

    private EnhancedRandom enchancedRandom;

    // #region SetUp
    
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        _sut = new NoteController(mockNoteService);
        enchancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
    }

    // #endregion

    // #region Constructor 

    @Test
    void Constructor_Throws_Exception_When_NoteService_Is_Null() throws Exception {
        // Arrange

        // Act
        var exception = assertThrows(NullPointerException.class, 
            () -> new NoteController(null), 
            "Expected constructor to throw a NullPointerException when noteService is null"
        );

        // Assert
        assertEquals("noteService must not be null", exception.getMessage());
    }

    // #endregion

    // #region getByPatientId

    // @Test
    // void getByPatientId_Calls_EncounterService_With_Request() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var response = enchancedRandom.nextObject(Result.class);
        
    //     when(mockNoteService.getByPatientId(id))
    //         .thenReturn(response);

    //     // Act
    //     _sut.getByPatientId(id);

    //     // Assert
    //     verify(mockNoteService, times(1)).getByPatientId(id);
    // }

    // @Test
    // void getByPatientId_Returns_BadRequest_When_ResultIsFailure() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockNoteService.getByPatientId(id))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.getByPatientId(id);

    //     // Assert
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

    // @Test
    // void getByPatientId_Returns_message_from_NoteService_When_ResultIsFailure() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockNoteService.getByPatientId(id))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.getByPatientId(id);

    //     // Assert
    //     assertEquals(message, response.getBody());
    // }

    // // #endregion

    // // #region getByStaffId

    // @Test
    // void getByStaffId_Calls_NoteService_With_Request() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var response = enchancedRandom.nextObject(Result.class);
        
    //     when(mockNoteService.getByStaffId(id))
    //         .thenReturn(response);

    //     // Act
    //     _sut.getByStaffId(id);

    //     // Assert
    //     verify(mockNoteService, times(1)).getByStaffId(id);
    // }

    // @Test
    // void getByStaffId_Returns_BadRequest_When_ResultIsFailure() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockNoteService.getByStaffId(id))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.getByStaffId(id);

    //     // Assert
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

    // @Test
    // void getByStaffId_Returns_message_from_NoteService_When_ResultIsFailure() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockNoteService.getByStaffId(id))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.getByStaffId(id);

    //     // Assert
    //     assertEquals(message, response.getBody());
    // }

    // // #endregion

    // // #region create

    // @Test
    // void create_Calls_NoteService_With_Request() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateRequest.class);
    //     var response = enchancedRandom.nextObject(Result.class);
        
    //     when(mockNoteService.create(request))
    //         .thenReturn(response);

    //     // Act
    //     _sut.create(request);

    //     // Assert
    //     verify(mockNoteService, times(1)).create(request);
    // }

    // @Test
    // void create_Returns_BadRequest_When_ResultIsFailure() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateRequest.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockNoteService.create(request))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.create(request);

    //     // Assert
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

    // @Test
    // void create_Returns_message_from_NoteService_When_ResultIsFailure() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateRequest.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockNoteService.create(request))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.create(request);

    //     // Assert
    //     assertEquals(message, response.getBody());
    // }

    // #endregion
}