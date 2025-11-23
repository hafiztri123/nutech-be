package com.nutech.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetServiceResponse {
    @JsonProperty("service_code")
    private String serviceCode;
    @JsonProperty("service_name")
    private String serviceName;
    
    @JsonProperty("service_icon")
    private String serviceIcon;
    
    
    @JsonProperty("service_tariff")
    private Long serviceTariff;

    public GetServiceResponse(String serviceCode, String serviceName, String serviceIcon, Long serviceTariff) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.serviceIcon = serviceIcon;
        this.serviceTariff = serviceTariff;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
    }

    public Long getServiceTariff() {
        return serviceTariff;
    }

    public void setServiceTariff(Long serviceTariff) {
        this.serviceTariff = serviceTariff;
    }

    
    
}
