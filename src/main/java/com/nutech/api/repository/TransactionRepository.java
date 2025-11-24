package com.nutech.api.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nutech.api.dto.response.GetTransactionHistory;
import com.nutech.api.model.Balance;
import com.nutech.api.model.Transaction;

@Repository
public class TransactionRepository {

    private JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.setQueryTimeout(10);
    }

    public Balance getBalance(UUID userId) {
        String sql = "SELECT id, user_id, balance, created_at, updated_at FROM balances WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Balance balance = new Balance();
            balance.setId(rs.getObject("id", UUID.class));
            balance.setUserId(rs.getObject("user_id", UUID.class));
            balance.setBalance(rs.getLong("balance"));
            balance.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            balance.setUpdatedAt(rs.getObject("updated_at", LocalDateTime.class));
            return balance;
        }, userId);
    }

    public void insertTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, service_id, invoice_number, transaction_type, amount) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                transaction.getUserId(),
                transaction.getServiceId(),
                transaction.getInvoiceNumber(),
                transaction.getTransactionType(),
                transaction.getAmount());
    }

    public Transaction getTransactionByInvoiceNumber(String invoiceNumber) {
        String sql = "SELECT id, user_id, service_id, invoice_number, transaction_type, amount, created_at FROM transactions WHERE invoice_number = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Transaction transaction = new Transaction();
            transaction.setId(rs.getObject("id", UUID.class));
            transaction.setUserId(rs.getObject("user_id", UUID.class));
            transaction.setServiceId(rs.getObject("service_id", UUID.class));
            transaction.setInvoiceNumber(rs.getString("invoice_number"));
            transaction.setTransactionType(rs.getString("transaction_type"));
            transaction.setAmount(rs.getLong("amount"));
            transaction.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            return transaction;
        }, invoiceNumber);
    }

    public List<GetTransactionHistory> getTransactionHistoryByUserId(UUID userId,
            Integer offset, Integer limit) {
        String baseSql = """
                        SELECT
                    t.invoice_number,
                    t.transaction_type,
                    COALESCE(s.name, 'Top Up balance') as description,
                    t.amount  as amount,
                    t.created_at
                FROM transactions t
                LEFT JOIN services s ON t.service_id = s.id
                WHERE t.user_id = ?
                ORDER BY t.created_at  DESC
                """;

        int safeOffset = (offset != null && offset >= 0) ? offset : 0;

        if (limit != null && limit > 0) {
            baseSql += " LIMIT ? OFFSET ?";

            return jdbcTemplate.query(baseSql, (rs, rowNum) -> mapRow(rs), userId, limit, safeOffset);
        }

        baseSql += " OFFSET ?";

        return jdbcTemplate.query(baseSql, (rs, rowNum) -> mapRow(rs), userId, safeOffset);
    }

    private GetTransactionHistory mapRow(ResultSet rs) throws SQLException {
        GetTransactionHistory history = new GetTransactionHistory();
        history.setInvoiceNumber(rs.getString("invoice_number"));
        history.setTransactionType(rs.getString("transaction_type"));
        history.setDescription(rs.getString("description"));
        history.setTotalAmount(rs.getLong("amount"));
        history.setCreatedOn(rs.getTimestamp("created_at").toLocalDateTime());
        return history;
    }

}
