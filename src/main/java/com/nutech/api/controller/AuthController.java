package com.nutech.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nutech.api.dto.request.LoginRequest;
import com.nutech.api.dto.request.RegistrationRequest;
import com.nutech.api.dto.response.GenericResponse;
import com.nutech.api.dto.response.LoginResponse;
import com.nutech.api.service.AuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<GenericResponse<Void>> register(@Valid @RequestBody RegistrationRequest request) {

        authService.register(request);

        return ResponseEntity.status(HttpStatus.OK).body(
            new GenericResponse<>(0, "Registrasi behasil, silahkan login")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse token = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>(0, "Login berhasil", token));
    }

}
