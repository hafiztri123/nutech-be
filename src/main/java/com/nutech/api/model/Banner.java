package com.nutech.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Banner {
    private UUID id;
    private String BannerName;
    private String BannerImage;
    private String BannerDescription;
    private LocalDateTime createdAt;


    public Banner() {}
    public Banner(UUID id, String bannerName, String bannerImage, String bannerDescription, LocalDateTime createdAt) {
        this.id = id;
        BannerName = bannerName;
        BannerImage = bannerImage;
        BannerDescription = bannerDescription;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBannerName() {
        return BannerName;
    }

    public void setBannerName(String bannerName) {
        BannerName = bannerName;
    }

    public String getBannerImage() {
        return BannerImage;
    }

    public void setBannerImage(String bannerImage) {
        BannerImage = bannerImage;
    }

    public String getBannerDescription() {
        return BannerDescription;
    }

    public void setBannerDescription(String bannerDescription) {
        BannerDescription = bannerDescription;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
