package com.nutech.api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nutech.api.model.Banner;
import com.nutech.api.model.Service;

@Repository
public class InformationRepository {
        private final JdbcTemplate jdbcTemplate;
    public InformationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.setQueryTimeout(10);
    }


        public List<Banner> getBanners() {
        String sql = "SELECT id, banner_name, banner_image, description, created_at FROM banners";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Banner banner = new Banner();
            banner.setId(rs.getObject("id", UUID.class));
            banner.setBannerName(rs.getString("banner_name"));
            banner.setBannerImage(rs.getString("banner_image"));
            banner.setBannerDescription(rs.getString("description"));
            banner.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            return banner;
        });
    }

        public List<Service> GetServices() {
        String sql = "SELECT id, service_code, service_name, service_icon, service_tariff, created_at FROM services";


        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Service service = new Service();
            service.setId(rs.getObject("id", UUID.class));
            service.setServiceCode(rs.getString("service_code"));
            service.setServiceName(rs.getString("service_name"));
            service.setServiceIcon(rs.getString("service_icon"));
            service.setServiceTariff(rs.getLong("service_tariff"));
            service.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return service;
        });
    }
    
}
