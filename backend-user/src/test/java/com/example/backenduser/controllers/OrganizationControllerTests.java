package com.example.backenduser.controllers;

import com.example.backenduser.interfaces.IOrganizationService;
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
public class OrganizationControllerTests {
  @Mock
    private IOrganizationService mockOrganizationService;

    private OrganizationController _sut;

    private EnhancedRandom enchancedRandom;

    // #region SetUp
    
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        _sut = new OrganizationController(mockOrganizationService);
        enchancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
    }

    // #endregion

    // #region Constructor 

    @Test
    void Constructor_Throws_Exception_When_UserService_Is_Null() throws Exception {
        // Arrange

        // Act
        var exception = assertThrows(NullPointerException.class, 
            () -> new OrganizationController(null), 
            "Expected constructor to throw a NullPointerException when organizationService is null"
        );

        // Assert
        assertEquals("organizationService must not be null", exception.getMessage());
    }

    // #endregion

    // #region getAllOrganizations

    @Test
    void getAllOrganizations_Calls_OrganizationService() {
        // Arrange
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockOrganizationService.getAllOrganizations())
            .thenReturn(response);

        // Act
        _sut.getAllOrganizations();

        // Assert
        verify(mockOrganizationService, times(1)).getAllOrganizations();
    }

    @Test
    void getAllOrganizations_Returns_StatusCode_500_When_ResultIsFailure() {
        // Arrange
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockOrganizationService.getAllOrganizations())
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.getAllOrganizations();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getAllOrganizations_Returns_message_from_OrganizationService_When_ResultIsFailure() {
        // Arrange
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockOrganizationService.getAllOrganizations())
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.getAllOrganizations();

        // Assert
        assertEquals(message, response.getBody());
    }

    // #endregion
  }
