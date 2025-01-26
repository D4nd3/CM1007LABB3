package com.example.search.controllers;

import com.example.search.interfaces.IPatientService;
import com.example.search.util.Result;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import jakarta.ws.rs.core.Response;
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

@ExtendWith(MockitoExtension.class)
class PatientControllerTests {

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
    void Constructor_Throws_Exception_When_PatientService_Is_Null() throws Exception {
        // Arrange

        // Act
        var exception = assertThrows(NullPointerException.class, 
            () -> new PatientController(null), 
            "Expected constructor to throw a NullPointerException when patientService is null"
        );

        // Assert
        assertEquals("patientService must not be null", exception.getMessage());
    }

    // #endregion

    // #region searchPatientsByName

    @Test
    void searchPatientsByName_Calls_PatientService_With_Name() {
        // Arrange
        var name = enchancedRandom.nextObject(String.class);
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockPatientService.findPatientsByName(name))
            .thenReturn(response);

        // Act
        _sut.searchPatientsByName(name);

        // Assert
        verify(mockPatientService, times(1)).findPatientsByName(name);
    }

    @Test
    void searchPatientsByName_Returns_BadRequest_When_ResultIsFailure() {
        // Arrange
        var name = enchancedRandom.nextObject(String.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPatientService.findPatientsByName(name))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.searchPatientsByName(name);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void searchPatientsByName_Returns_message_from_PatientService_When_ResultIsFailure() {
        // Arrange
        var name = enchancedRandom.nextObject(String.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPatientService.findPatientsByName(name))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.searchPatientsByName(name);

        // Assert
        assertEquals(message, response.getEntity());
    }

    // #endregion

    // #region searchPatientsByCondition

    @Test
    void searchPatientsByCondition_Calls_PatientService_With_Condition() {
        // Arrange
        var condition = enchancedRandom.nextObject(String.class);
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockPatientService.findPatientsByCondition(condition))
            .thenReturn(response);

        // Act
        _sut.searchPatientsByCondition(condition);

        // Assert
        verify(mockPatientService, times(1)).findPatientsByCondition(condition);
    }

    @Test
    void searchPatientsByCondition_Returns_BadRequest_When_ResultIsFailure() {
        // Arrange
        var condition = enchancedRandom.nextObject(String.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPatientService.findPatientsByCondition(condition))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.searchPatientsByCondition(condition);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void searchPatientsByCondition_Returns_message_from_PatientService_When_ResultIsFailure() {
        // Arrange
        var condition = enchancedRandom.nextObject(String.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPatientService.findPatientsByCondition(condition))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.searchPatientsByCondition(condition);

        // Assert
        assertEquals(message, response.getEntity());
    }

    // #endregion

    // #region searchPatientsByStaffName

    @Test
    void searchPatientsByStaffName_Calls_PatientService_With_Name() {
        // Arrange
        var name = enchancedRandom.nextObject(String.class);
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockPatientService.findPatientsByStaffName(name))
            .thenReturn(response);

        // Act
        _sut.searchPatientsByStaffName(name);

        // Assert
        verify(mockPatientService, times(1)).findPatientsByStaffName(name);
    }

    @Test
    void searchPatientsByStaffName_Returns_BadRequest_When_ResultIsFailure() {
        // Arrange
        var name = enchancedRandom.nextObject(String.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPatientService.findPatientsByStaffName(name))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.searchPatientsByStaffName(name);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void CreateCondition_Returns_message_from_PatientService_When_ResultIsFailure() {
        // Arrange
        var name = enchancedRandom.nextObject(String.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPatientService.findPatientsByStaffName(name))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.searchPatientsByStaffName(name);

        // Assert
        assertEquals(message, response.getEntity());
    }

    // #endregion
}