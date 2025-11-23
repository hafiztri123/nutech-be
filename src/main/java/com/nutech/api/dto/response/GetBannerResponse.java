package com.nutech.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetBannerResponse {
    @JsonProperty("banner_name")
    private String bannerName;

    @JsonProperty("banner_image")
    private String bannerImage;

    private String description;



    public GetBannerResponse(String bannerName, String bannerImage, String bannerDescription) {
        this.bannerName = bannerName;
        this.bannerImage = bannerImage;
        this.description = bannerDescription;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String bannerDescription) {
        this.description = bannerDescription;
    }
}