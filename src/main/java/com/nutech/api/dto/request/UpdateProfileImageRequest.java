package com.nutech.api.dto.request;

import org.springframework.web.multipart.MultipartFile;


public class UpdateProfileImageRequest {

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
