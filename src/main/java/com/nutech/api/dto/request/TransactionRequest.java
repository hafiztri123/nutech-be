package com.nutech.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;

public class TransactionRequest {


    @NotEmpty(message = "Service atau Layanan tidak ditemukan")
    @JsonProperty("service_code")
    private String serviceCode;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
