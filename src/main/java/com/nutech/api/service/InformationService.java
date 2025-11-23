package com.nutech.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.nutech.api.dto.response.GetBannerResponse;
import com.nutech.api.dto.response.GetServiceResponse;
import com.nutech.api.model.Banner;
import com.nutech.api.model.Service;
import com.nutech.api.repository.InformationRepository;

@org.springframework.stereotype.Service
public class InformationService {
    private final InformationRepository informationRepository;
    private final String apiPath;

    public InformationService(InformationRepository informationRepository, @Value("${api.path}") String apiPath) {
        this.apiPath = apiPath;
        this.informationRepository = informationRepository;

    }

    public List<GetBannerResponse>  getBanners() {
        List<Banner> banners = informationRepository.getBanners();

        List<GetBannerResponse> responseList = new ArrayList<>();

        for (Banner b: banners) {
            responseList.add(new GetBannerResponse(
                b.getBannerName(),
                apiPath + "/" + b.getBannerImage(),
                b.getBannerDescription()
            ));
        }

        return responseList;
    }

    public List<GetServiceResponse> getServices() {
        List<Service> services = informationRepository.GetServices();

        List<GetServiceResponse> responseList = new ArrayList<>();

        for (Service s: services) {
            responseList.add(new GetServiceResponse(
                s.getServiceCode(),
                s.getServiceName(),
                apiPath + "/" + s.getServiceIcon(),
                s.getServiceTariff()
            ));
        }

        return responseList;
    }

    
    
}
