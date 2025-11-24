package com.nutech.api.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BalanceRepository {

    private final JdbcTemplate jdbcTemplate;

    public BalanceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.setQueryTimeout(10);
    }

    public void initializeBalance(UUID userId) {
        String sql = "INSERT INTO balances (user_id, balance) VALUES (?, ?)";

        try {
            jdbcTemplate.execute((Connection conn) -> {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setObject(1, userId);
                    ps.setLong(2, 0L);
                    ps.executeUpdate();
                    return null;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Error initializing balance", e);
        }
    }

    public Integer updateBalance(UUID userId, Long balance) {
        String sql = "UPDATE balances SET balance = balance + ?, updated_at = NOW() WHERE user_id = ? AND balance + ? >= 0";

        return jdbcTemplate.update(sql, balance, userId, balance);
    }

}
