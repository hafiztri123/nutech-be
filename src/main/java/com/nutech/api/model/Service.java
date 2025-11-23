package com.nutech.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Service {
    private UUID id;
    private String ServiceCode;
    private String serviceName;
    private String serviceIcon;
    private Long serviceTariff;
    private LocalDateTime createdAt;

    public Service() {}
    public Service(UUID id, String serviceName, String serviceCode, String serviceIcon, Long serviceTariff, LocalDateTime createdAt) {
        this.id = id;
        this.serviceName = serviceName;
        this.serviceIcon = serviceIcon;
        this.serviceTariff = serviceTariff;
        this.createdAt = createdAt;
        this.ServiceCode = serviceCode;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getServiceCode() {
        return this.ServiceCode;
    }
    public void setServiceCode(String serviceCode) {
        this.ServiceCode = serviceCode;
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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


