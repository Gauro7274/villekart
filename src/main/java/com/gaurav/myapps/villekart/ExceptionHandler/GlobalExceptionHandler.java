package com.gaurav.myapps.villekart.ExceptionHandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gaurav.myapps.villekart.utils.StringConstants;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle DataIntegrityViolationException (for database constraint violations)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String responseMessage = "";

        // Customize error messages based on the exception message
        if (ex.getMessage().contains("Duplicate entry")) {
            responseMessage = StringConstants.USERNAME_ALREADY_PRESENT.getValue();
        } else if (ex.getMessage().contains("not-null") && ex.getMessage().contains("username")) {
            responseMessage = StringConstants.USERNAME_NOT_NULL.getValue();
        } else if (ex.getMessage().contains("not-null") && ex.getMessage().contains("role")) {
            responseMessage = StringConstants.ROLE_NOT_NULL.getValue();
        } else {
            responseMessage = StringConstants.USERNAME_ALREADY_PRESENT.getValue();
        }

        // Return a custom response with the appropriate HTTP status
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    // Handle IllegalArgumentException (for invalid arguments like null password)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        String responseMessage = "";

        if (ex.getMessage().contains("rawPassword cannot be null")) {
            responseMessage = StringConstants.PASSWORD_NOT_NULL.getValue();
        }

        // Return a custom response with the appropriate HTTP status
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }
    
    // Handle BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        // Return custom error message for bad credentials
        String responseMessage = "Invalid username or password. Please try again.";
        return new ResponseEntity<>(responseMessage, HttpStatus.UNAUTHORIZED);
    }

    // Optionally, handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        // Log the exception and return a generic error message
        String responseMessage = "An unexpected error occurred. Please try again.";
        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

