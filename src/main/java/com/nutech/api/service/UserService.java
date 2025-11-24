package com.nutech.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nutech.api.dto.request.UpdateProfileRequest;
import com.nutech.api.dto.response.GetProfileResponse;
import com.nutech.api.exception.InvalidCredentialsException;
import com.nutech.api.exception.InvalidFileFormatException;
import com.nutech.api.model.User;
import com.nutech.api.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final String apiPath; 

    public UserService(UserRepository userRepository,
            FileStorageService fileStorageService,
            @Value("${api.path}") String apiPath) {
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        this.apiPath = apiPath;
    }

    public GetProfileResponse getProfile(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new InvalidCredentialsException("username atau password salah"));

        String profileUrl = user.getProfileImage();
        if (profileUrl != null) {
            profileUrl = apiPath + "/image/" + user.getProfileImage();
        }

        return new GetProfileResponse(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                profileUrl);
    }

    @Transactional
    public GetProfileResponse updateProfile(String userEmail, UpdateProfileRequest request) {
        userRepository.updateProfile(request.getFirstName(), request.getLastName(), userEmail);
        return getProfile(userEmail);
    }

    @Transactional
    public GetProfileResponse updateProfileImage(MultipartFile file, String userEmail) {
        try {
            if (file == null || file.isEmpty()) {
                throw new InvalidFileFormatException("File tidak boleh kosong");
            }

            String contentType = file.getContentType();
            if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
                throw new InvalidFileFormatException("Format image tidak sesuai");
            }

            User user = userRepository.findByEmail(userEmail).orElseThrow(
                    () -> new InvalidCredentialsException("username atau password salah"));


            if (user.getProfileImage() != null) {
                fileStorageService.deleteFile(user.getProfileImage());
            }

            String fileName = fileStorageService.storeFile(file);

            userRepository.updateProfileImage(fileName, userEmail);

            return getProfile(userEmail);

        } catch (InvalidFileFormatException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error updating user profile image", e);
        }
    }
}