package com.nutech.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nutech.api.dto.response.GenericResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .findFirst()
        .map(FieldError::getDefaultMessage)
        .orElse("Validation error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new GenericResponse<>(102, errorMessage)
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<GenericResponse<Void>> handleUserAlreadyExists(
            UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GenericResponse<>(102, ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<GenericResponse<Void>> handleInvalidCredentials(
            InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GenericResponse<>(103, ex.getMessage()));
    }

    @ExceptionHandler(InvalidFileFormatException.class) 
    public ResponseEntity<GenericResponse<Void>> handleInvalidFileFormat(
            InvalidFileFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GenericResponse<>(102, ex.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Void>> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GenericResponse<>(500, "Internal server error"));
    }



}
