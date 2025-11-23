package com.nutech.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import com.nutech.api.model.User;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Statement;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.jdbc.setQueryTimeout(10);
    }

    public void save(User user) {
        String sql = "INSERT INTO users (email, password, first_name, last_name) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail().toLowerCase());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName().toLowerCase());
            ps.setString(4, user.getLastName().toLowerCase());
            return ps;
        }, keyHolder);

        UUID generatedId = (UUID) keyHolder.getKeys().get("id");
        user.setId(generatedId);
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, email, password, first_name, last_name, profile_image, created_at, updated_at FROM users WHERE email = ?";

        try {
            Optional<User> result = jdbc.queryForObject(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getObject("id", UUID.class));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setProfileImage(rs.getString("profile_image"));
                user.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                user.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
                return Optional.of(user);
            }, email);

            if (result.isEmpty()) {
                return Optional.empty();
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error getting user by email", e);
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try {
            return jdbc.queryForObject(sql, (rs, rowNum) -> {
                return rs.getInt(1);
            }, email) > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking if user exists", e);
        }
    }

    public void updateProfile(String firstName, String lastName, String email) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, updated_at = ? WHERE email = ?";

        try {
            jdbc.update(sql, ps -> {
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(4, email);
            });
        } catch (Exception e) {
            throw new RuntimeException("Error updating user profile", e);
        }
    }

    public void updateProfileImage(String profileImage, String email) {
        String sql = "UPDATE users SET profile_image = ?, updated_at = ? WHERE email = ?";
        try {
            jdbc.update(sql, ps -> {
                ps.setString(1, profileImage);
                ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(3, email);
            });
        } catch (Exception e) {
            throw new RuntimeException("Error updating user profile image", e);
        }
    }
}