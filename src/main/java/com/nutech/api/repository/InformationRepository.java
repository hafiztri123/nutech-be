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
        String sql = "SELECT id, name, image, description, created_at FROM banners";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Banner banner = new Banner();
            banner.setId(rs.getObject("id", UUID.class));
            banner.setBannerName(rs.getString("name"));
            banner.setBannerImage(rs.getString("image"));
            banner.setBannerDescription(rs.getString("description"));
            banner.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            return banner;
        });
    }

        public List<Service> GetServices() {
        String sql = "SELECT id, code, name, icon, tariff, created_at FROM services";


        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Service service = new Service();
            service.setId(rs.getObject("id", UUID.class));
            service.setServiceCode(rs.getString("code"));
            service.setServiceName(rs.getString("name"));
            service.setServiceIcon(rs.getString("icon"));
            service.setServiceTariff(rs.getLong("tariff"));
            service.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return service;
        });
    }

    public Boolean isServiceExistsByCode(String code) {
        String sql = "SELECT COUNT(*) FROM services WHERE code = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            return rs.getInt(1);
        }, code) > 0;
    }

    public Service getServiceByCode(String code) {
        String sql = "SELECT id, code, name, icon, tariff, created_at FROM services WHERE code = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Service service = new Service();
            service.setId(rs.getObject("id", UUID.class));
            service.setServiceCode(rs.getString("code"));
            service.setServiceName(rs.getString("name"));
            service.setServiceIcon(rs.getString("icon"));
            service.setServiceTariff(rs.getLong("tariff"));
            service.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return service;
        }, code);
    }
    
}
