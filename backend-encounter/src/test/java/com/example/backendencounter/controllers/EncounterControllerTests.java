package com.example.backendencounter.controllers;


import com.example.backendencounter.dto.requests.observations.*;
import com.example.backendencounter.interfaces.IEncounterService;
import com.example.backendencounter.util.Result;

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
class EncounterControllerTests {

    @Mock
    private IEncounterService mockEncounterService;

    private EncounterController _sut;

    private EnhancedRandom enchancedRandom;

    // #region SetUp
    
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        _sut = new EncounterController(mockEncounterService);
        enchancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
    }

    // #endregion

    // #region Constructor 

    @Test
    void Constructor_Throws_Exception_When_EncounterService_Is_Null() throws Exception {
        // Arrange

        // Act
        var exception = assertThrows(NullPointerException.class, 
            () -> new EncounterController(null), 
            "Expected constructor to throw a NullPointerException when encounterService is null"
        );

        // Assert
        assertEquals("encounterService must not be null", exception.getMessage());
    }

    // #endregion

    // #region CreateEncounter

    // @Test
    // void CreateEncounter_Calls_EncounterService_With_Request() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateEncounterRequest.class);
    //     var response = enchancedRandom.nextObject(Result.class);
        
    //     when(mockEncounterService.addEncounter(request))
    //         .thenReturn(response);

    //     // Act
    //     _sut.CreateEncounter(request);

    //     // Assert
    //     verify(mockEncounterService, times(1)).addEncounter(request);
    // }

    // @Test
    // void CreateEncounter_Returns_BadRequest_When_ResultIsFailure() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateEncounterRequest.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockEncounterService.addEncounter(request))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.CreateEncounter(request);

    //     // Assert
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

    // @Test
    // void CreateEncounter_Returns_message_from_EncounterService_When_ResultIsFailure() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateEncounterRequest.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockEncounterService.addEncounter(request))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.CreateEncounter(request);

    //     // Assert
    //     assertEquals(message, response.getBody());
    // }

    // // #endregion

    // // #region CreateObservation

    // @Test
    // void CreateObservation_Calls_EncounterService_With_Request() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateObservationRequest.class);
    //     var response = enchancedRandom.nextObject(Result.class);
        
    //     when(mockEncounterService.addObservation(request))
    //         .thenReturn(response);

    //     // Act
    //     _sut.CreateObservation(request);

    //     // Assert
    //     verify(mockEncounterService, times(1)).addObservation(request);
    // }

    // @Test
    // void CreateObservation_Returns_BadRequest_When_ResultIsFailure() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateObservationRequest.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockEncounterService.addObservation(request))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.CreateObservation(request);

    //     // Assert
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

    // @Test
    // void CreateObservation_Returns_message_from_EncounterService_When_ResultIsFailure() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateObservationRequest.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockEncounterService.addObservation(request))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.CreateObservation(request);

    //     // Assert
    //     assertEquals(message, response.getBody());
    // }

    // // #endregion

    // // #region CreateCondition

    // @Test
    // void CreateCondition_Calls_EncounterService_With_Request() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateConditionRequest.class);
    //     var response = enchancedRandom.nextObject(Result.class);
        
    //     when(mockEncounterService.addCondition(request))
    //         .thenReturn(response);

    //     // Act
    //     _sut.CreateCondition(request);

    //     // Assert
    //     verify(mockEncounterService, times(1)).addCondition(request);
    // }

    // @Test
    // void CreateCondition_Returns_BadRequest_When_ResultIsFailure() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateConditionRequest.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockEncounterService.addCondition(request))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.CreateCondition(request);

    //     // Assert
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

    // @Test
    // void CreateCondition_Returns_message_from_EncounterService_When_ResultIsFailure() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(CreateConditionRequest.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockEncounterService.addCondition(request))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.CreateCondition(request);

    //     // Assert
    //     assertEquals(message, response.getBody());
    // }

    // // #endregion

    // // #region GetByStaffId

    // @Test
    // void GetByStaffId_Calls_EncounterService_With_Id() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var response = enchancedRandom.nextObject(Result.class);
        
    //     when(mockEncounterService.getEncountersByStaffId(id))
    //         .thenReturn(response);

    //     // Act
    //     _sut.GetByStaffId(id);

    //     // Assert
    //     verify(mockEncounterService, times(1)).getEncountersByStaffId(id);
    // }

    // @Test
    // void GetByStaffId_Returns_StatusCode_NotFound_When_ResultIsFailure() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockEncounterService.getEncountersByStaffId(id))
    //     .thenReturn(new Result<>(false, message));
            

    //     // Act
    //     var response = _sut.GetByStaffId(id);

    //     // Assert
    //     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    // }

    // @Test
    // void GetByStaffId_Returns_message_from_EncounterService_When_ResultIsFailure() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockEncounterService.getEncountersByStaffId(id))
    //     .thenReturn(new Result<>(false, message));
    //     // Act
    //     var response = _sut.GetByStaffId(id);

    //     // Assert
    //     assertEquals(message, response.getBody());
    // }

    // // #endregion

    // // #region GetByPatientId

    // @Test
    // void GetByPatientId_Calls_EncounterService_With_Id() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var response = enchancedRandom.nextObject(Result.class);
        
    //     when(mockEncounterService.getEncountersByPatientId(id))
    //         .thenReturn(response);

    //     // Act
    //     _sut.GetByPatientId(id);

    //     // Assert
    //     verify(mockEncounterService, times(1)).getEncountersByPatientId(id);
    // }

    // @Test
    // void GetByPatientId_Returns_BadRequest_When_ResultIsFailure() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockEncounterService.getEncountersByPatientId(id))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.GetByPatientId(id);

    //     // Assert
    //     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    // }

    // @Test
    // void GetByPatientId_Returns_message_from_EncounterService_When_ResultIsFailure() {
    //     // Arrange
    //     var id = enchancedRandom.nextObject(String.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockEncounterService.getEncountersByPatientId(id))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.GetByPatientId(id);

    //     // Assert
    //     assertEquals(message, response.getBody());
    // }

    //#endregion

}