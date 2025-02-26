package com.example.backenduser.controllers;

import com.example.backenduser.dto.requests.users.CreateUserRequest;
import com.example.backenduser.interfaces.IUserService;
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
class UserControllerTests {

    @Mock
    private IUserService mockUserService;

    private UserController _sut;

    private EnhancedRandom enchancedRandom;

    // #region SetUp
    
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        _sut = new UserController(mockUserService);
        enchancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
    }

    // #endregion

    // #region Constructor 

    @Test
    void Constructor_Throws_Exception_When_UserService_Is_Null() throws Exception {
        // Arrange

        // Act
        var exception = assertThrows(NullPointerException.class, 
            () -> new UserController(null), 
            "Expected constructor to throw a NullPointerException when userService is null"
        );

        // Assert
        assertEquals("userService must not be null", exception.getMessage());
    }

    // #endregion

    // #region registerUser

    @Test
    void registerUser_Calls_UserService_With_Request() {
        // Arrange
        var request = enchancedRandom.nextObject(CreateUserRequest.class);
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockUserService.registerUser(request))
            .thenReturn(response);

        // Act
        _sut.registerUser(request);

        // Assert
        verify(mockUserService, times(1)).registerUser(request);
    }

    @Test
    void registerUser_Returns_BadRequest_When_ResultIsFailure() {
        // Arrange
        var request = enchancedRandom.nextObject(CreateUserRequest.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockUserService.registerUser(request))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.registerUser(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void registerUser_Returns_message_from_UserService_When_ResultIsFailure() {
        // Arrange
        var request = enchancedRandom.nextObject(CreateUserRequest.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockUserService.registerUser(request))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.registerUser(request);

        // Assert
        assertEquals(message, response.getBody());
    }

    // #endregion

    // #region loginUser

    // @Test
    // void loginUser_Calls_UserService_With_Request() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(LoginRequest.class);
    //     var response = enchancedRandom.nextObject(Result.class);
        
    //     when(mockUserService.authenticateUser(request))
    //         .thenReturn(response);

    //     // Act
    //     _sut.loginUser(request);

    //     // Assert
    //     verify(mockUserService, times(1)).authenticateUser(request);
    // }

    // @Test
    // void loginUser_Returns_UnAuthorized_When_ResultIsFailure() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(LoginRequest.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockUserService.authenticateUser(request))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.loginUser(request);

    //     // Assert
    //     assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    // }

    // @Test
    // void loginUser_Returns_message_from_UserService_When_ResultIsFailure() {
    //     // Arrange
    //     var request = enchancedRandom.nextObject(LoginRequest.class);
    //     var message = enchancedRandom.nextObject(String.class);
        
    //     when(mockUserService.authenticateUser(request))
    //         .thenReturn(new Result<>(false, message));

    //     // Act
    //     var response = _sut.loginUser(request);

    //     // Assert
    //     assertEquals(message, response.getBody());
    // }

    // #endregion

    // #region getAllStaff

    @Test
    void getAllStaff_Calls_UserService() {
        // Arrange
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockUserService.getAllStaff())
            .thenReturn(response);

        // Act
        _sut.getAllStaff();

        // Assert
        verify(mockUserService, times(1)).getAllStaff();
    }

    @Test
    void getAllStaff_Returns_StatusCode_500_When_ResultIsFailure() {
        // Arrange
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockUserService.getAllStaff())
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.getAllStaff();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getAllStaff_Returns_message_from_UserService_When_ResultIsFailure() {
        // Arrange
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockUserService.getAllStaff())
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.getAllStaff();

        // Assert
        assertEquals(message, response.getBody());
    }

    // #endregion

    // #region getAll

    @Test
    void getAll_Calls_UserService() {
        // Arrange
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockUserService.getAll())
            .thenReturn(response);

        // Act
        _sut.getAll();

        // Assert
        verify(mockUserService, times(1)).getAll();
    }

    @Test
    void getAll_Returns_StatusCode_500_When_ResultIsFailure() {
        // Arrange
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockUserService.getAll())
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.getAll();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getAll_Returns_message_from_UserService_When_ResultIsFailure() {
        // Arrange
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockUserService.getAll())
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.getAll();

        // Assert
        assertEquals(message, response.getBody());
    }

    // #endregion

    // #region getById

    @Test
    void getById_Calls_UserService_With_Id() {
        // Arrange
        var id = enchancedRandom.nextObject(String.class);
        var response = enchancedRandom.nextObject(Result.class);
        
        when(mockUserService.getUserById(id))
            .thenReturn(response);

        // Act
        _sut.getById(id);

        // Assert
        verify(mockUserService, times(1)).getUserById(id);
    }

    @Test
    void getById_Returns_BadRequest_When_ResultIsFailure() {
        // Arrange
        var id = enchancedRandom.nextObject(String.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockUserService.getUserById(id))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.getById(id);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getById_Returns_message_from_UserService_When_ResultIsFailure() {
        // Arrange
        var id = enchancedRandom.nextObject(String.class);
        var message = enchancedRandom.nextObject(String.class);
        
        when(mockUserService.getUserById(id))
            .thenReturn(new Result<>(false, message));

        // Act
        var response = _sut.getById(id);

        // Assert
        assertEquals(message, response.getBody());
    }

    //#endregion

}