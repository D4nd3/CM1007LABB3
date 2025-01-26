package com.example.backenduser.controllers;

import com.example.backenduser.interfaces.ILocationService;
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
public class LocationControllerTests {
  @Mock
    private ILocationService mockLocationService;

    private LocationController _sut;

    private EnhancedRandom enchancedRandom;

    // #region SetUp
    
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        _sut = new LocationController(mockLocationService);
        enchancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
    }

    // #endregion

    // #region Constructor 

    @Test
    void Constructor_Throws_Exception_When_UserService_Is_Null() throws Exception {
        // Arrange

        // Act
        var exception = assertThrows(NullPointerException.class, 
            () -> new LocationController(null), 
            "Expected constructor to throw a NullPointerException when locationService is null"
        );

        // Assert
        assertEquals("locationService must not be null", exception.getMessage());
    }

    // #endregion

    // #region get

    @Test
    void get_Calls_LocationService() {
        // Arrange
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockLocationService.getAll())
            .thenReturn(response);

        // Act
        _sut.get();

        // Assert
        verify(mockLocationService, times(1)).getAll();
    }

    @Test
    void get_Returns_StatusCode_500_When_ResultIsFailure() {
        // Arrange
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockLocationService.getAll())
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.get();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void get_Returns_message_from_LocationService_When_ResultIsFailure() {
        // Arrange
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockLocationService.getAll())
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.get();

        // Assert
        assertEquals(message, response.getBody());
    }

    // #endregion
  }
