package com.nutech.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutech.api.dto.response.GenericResponse;
import com.nutech.api.dto.response.GetBannerResponse;
import com.nutech.api.dto.response.GetServiceResponse;
import com.nutech.api.service.InformationService;

@RestController
public class InformationController {
    private final InformationService informationService;

    public InformationController(InformationService bannerService) {
        this.informationService = bannerService;
    }


    @GetMapping("/banner")
    public ResponseEntity<GenericResponse<List<GetBannerResponse>>> getBanners() {

        List<GetBannerResponse> response = informationService.getBanners();

        return ResponseEntity.status(HttpStatus.OK).body(
                new GenericResponse<List<GetBannerResponse>>(0, "Sukses", response));
    }

    @GetMapping("/services")
    public ResponseEntity<GenericResponse<List<GetServiceResponse>>> getServices() {

        List<GetServiceResponse> response = informationService.getServices();

        return ResponseEntity.status(HttpStatus.OK).body(
                new GenericResponse<List<GetServiceResponse>>(0, "Sukses", response));

    }


}
