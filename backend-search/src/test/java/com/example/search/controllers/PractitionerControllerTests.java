package com.example.search.controllers;

import com.example.search.dto.requests.PractitionerAndDateRequest;
import com.example.search.interfaces.IPractitionerService;
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
class PractitionerControllerTests {

    @Mock
    private IPractitionerService mockPractitionerService;

    private PractitionerController _sut;

    private EnhancedRandom enchancedRandom;

    // #region SetUp
    
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        _sut = new PractitionerController(mockPractitionerService);
        enchancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
    }

    // #endregion

    // #region Constructor 

    @Test
    void Constructor_Throws_Exception_When_PractitionerService_Is_Null() throws Exception {
        // Arrange

        // Act
        var exception = assertThrows(NullPointerException.class, 
            () -> new PractitionerController(null), 
            "Expected constructor to throw a NullPointerException when practitionerService is null"
        );

        // Assert
        assertEquals("practitionerService must not be null", exception.getMessage());
    }

    // #endregion

    // #region searchPatientsByPractitioner

    @Test
    void searchPatientsByPractitioner_Calls_PractitionerService_With_Id() {
        // Arrange
        var id = enchancedRandom.nextObject(int.class);
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockPractitionerService.PatientsByPractitioner(id))
            .thenReturn(response);

        // Act
        _sut.searchPatientsByPractitioner(id);

        // Assert
        verify(mockPractitionerService, times(1)).PatientsByPractitioner(id);
    }

    @Test
    void searchPatientsByPractitioner_Returns_BadRequest_When_ResultIsFailure() {
        // Arrange
        var id = enchancedRandom.nextObject(int.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPractitionerService.PatientsByPractitioner(id))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.searchPatientsByPractitioner(id);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void searchPatientsByPractitioner_Returns_message_from_PractitionerService_When_ResultIsFailure() {
        // Arrange
        var id = enchancedRandom.nextObject(int.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPractitionerService.PatientsByPractitioner(id))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.searchPatientsByPractitioner(id);

        // Assert
        assertEquals(message, response.getEntity());
    }

    // #endregion

    // #region searchPatientsByPractitionerAndDate

    @Test
    void searchPatientsByPractitionerAndDate_Calls_PractitionerService_With_Condition() {
        // Arrange
        var request = enchancedRandom.nextObject(PractitionerAndDateRequest.class);
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockPractitionerService.PatientsByPractitionerAndDay(request))
            .thenReturn(response);

        // Act
        _sut.searchPatientsByPractitionerAndDate(request);

        // Assert
        verify(mockPractitionerService, times(1)).PatientsByPractitionerAndDay(request);
    }

    @Test
    void searchPatientsByPractitionerAndDate_Returns_BadRequest_When_ResultIsFailure() {
        // Arrange
        var request = enchancedRandom.nextObject(PractitionerAndDateRequest.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPractitionerService.PatientsByPractitionerAndDay(request))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.searchPatientsByPractitionerAndDate(request);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void searchPatientsByPractitionerAndDate_Returns_message_from_PractitionerService_When_ResultIsFailure() {
        // Arrange
        var request = enchancedRandom.nextObject(PractitionerAndDateRequest.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockPractitionerService.PatientsByPractitionerAndDay(request))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.searchPatientsByPractitionerAndDate(request);

        // Assert
        assertEquals(message, response.getEntity());
    }

    // #endregion
}