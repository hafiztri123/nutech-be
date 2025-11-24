package com.nutech.api.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.nutech.api.dto.response.GenericResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<GenericResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {

                String errorMessage = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .findFirst()
                                .map(FieldError::getDefaultMessage)
                                .orElse("Validation error");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                                new GenericResponse<>(102, errorMessage));
        }

        @ExceptionHandler(UserAlreadyExistsException.class)
        public ResponseEntity<GenericResponse<Void>> handleUserAlreadyExists(
                        UserAlreadyExistsException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new GenericResponse<>(102, ex.getMessage()));
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<GenericResponse<Void>> handleHttpMessageNotReadable(
                        HttpMessageNotReadableException ex) {

                Throwable cause = ex.getCause();

                if (cause instanceof InvalidFormatException) {
                        InvalidFormatException e = (InvalidFormatException) cause;
                        String fieldName = e.getPath().get(0).getFieldName();
                        if ("top_up_amount".equals(fieldName)) {
                                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                                .body(new GenericResponse<>(102,
                                                                "Parameter amount hanya boleh angka dan tidak boleh lebih kecil dari 0"));

                        }
                }

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

        @ExceptionHandler(ItemNotFoundException.class)
        public ResponseEntity<GenericResponse<Void>> handleNotFoundException(ItemNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new GenericResponse<>(102, ex.getMessage()));
        }

        @ExceptionHandler(InvalidAction.class)
        public ResponseEntity<GenericResponse<Void>> handleInvalidAction(InvalidAction ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new GenericResponse<>(102, ex.getMessage()));
        }

        @ExceptionHandler(NoHandlerFoundException.class)
        public ResponseEntity<GenericResponse<Void>> handleNotFound(NoHandlerFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new GenericResponse<>(404, "Url tidak ditemukan"));
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<GenericResponse<Void>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                                .body(new GenericResponse<>(405, "Method tidak didukung"));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<GenericResponse<Void>> handleGenericException(Exception ex) {
                logger.error(ex.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new GenericResponse<>(500, "Internal server error"));
        }

}
