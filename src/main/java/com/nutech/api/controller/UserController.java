package com.nutech.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nutech.api.dto.request.UpdateProfileImageRequest;
import com.nutech.api.dto.request.UpdateProfileRequest;
import com.nutech.api.dto.response.GenericResponse;
import com.nutech.api.dto.response.GetProfileResponse;
import com.nutech.api.model.User;
import com.nutech.api.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<GenericResponse<GetProfileResponse>> getProfile(@AuthenticationPrincipal User user) {

        GetProfileResponse resp = userService.getProfile(user.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(
                new GenericResponse<GetProfileResponse>(0, "Sukses", resp));
    }

    @PutMapping("/profile/update")
    public ResponseEntity<GenericResponse<GetProfileResponse>> updateProfile(@AuthenticationPrincipal User user,
            @RequestBody @Valid UpdateProfileRequest request) {

        GetProfileResponse resp = userService.updateProfile(user.getEmail(), request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new GenericResponse<GetProfileResponse>(0, "Update Profile berhasil", resp));
    }

    @PutMapping("/profile/image")
    public ResponseEntity<GenericResponse<GetProfileResponse>> updateProfileImage(
            @AuthenticationPrincipal User user,
            @ModelAttribute UpdateProfileImageRequest request) {

        GetProfileResponse resp = userService.updateProfileImage(request.getFile(), user.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(
                new GenericResponse<GetProfileResponse>(0, "Update Profile Image berhasil", resp));

    }

}
