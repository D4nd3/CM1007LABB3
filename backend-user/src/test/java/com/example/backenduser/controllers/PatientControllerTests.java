package com.example.backenduser.controllers;

import com.example.backenduser.interfaces.IPatientService;
import com.example.backenduser.util.Result;

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
public class PatientControllerTests {
  @Mock
    private IPatientService mockPatientService;

    private PatientController _sut;

    private EnhancedRandom enchancedRandom;

    // #region SetUp
    
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        _sut = new PatientController(mockPatientService);
        enchancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
    }

    // #endregion

    // #region Constructor 

    @Test
    void Constructor_Throws_Exception_When_UserService_Is_Null() throws Exception {
        // Arrange

        // Act
        var exception = assertThrows(NullPointerException.class, 
            () -> new PatientController(null), 
            "Expected constructor to throw a NullPointerException when userService is null"
        );

        // Assert
        assertEquals("patientService must not be null", exception.getMessage());
    }

    // #endregion

    // #region getAllPatients

    @Test
    void getAllPatients_Calls_PatientService() {
        // Arrange
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockPatientService.getAll())
            .thenReturn(response);

        // Act
        _sut.getAllPatients();

        // Assert
        verify(mockPatientService, times(1)).getAll();
    }

    @Test
    void getAllPatients_Returns_StatusCode_500_When_ResultIsFailure() {
        // Arrange
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPatientService.getAll())
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.getAllPatients();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getAllPatients_Returns_message_from_PatientService_When_ResultIsFailure() {
        // Arrange
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPatientService.getAll())
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.getAllPatients();

        // Assert
        assertEquals(message, response.getBody());
    }

    // #endregion
  }
