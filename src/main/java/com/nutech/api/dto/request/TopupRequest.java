package com.nutech.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;

public class TopupRequest {
    @JsonProperty("top_up_amount")
    @Min(value = 1, message = "Parameter amount hanya boleh angka dan tidak boleh lebih kecil dari 0")

    private Long topupAmount;

    public TopupRequest() {
    }

    public TopupRequest(Long topupAmount) {
        this.topupAmount = topupAmount;
    }

    public Long getTopupAmount() {
        return topupAmount;
    }

    public void setTopupAmount(Long topupAmount) {
        this.topupAmount = topupAmount;
    }

}
