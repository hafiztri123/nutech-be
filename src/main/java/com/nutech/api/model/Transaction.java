package com.nutech.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private UUID userId;
    private UUID serviceId;
    private String invoiceNumber;
    private String transactionType;
    private Long amount;
    private LocalDateTime createdAt;

    public Transaction() {}
    public Transaction(UUID id, UUID userId, UUID serviceId, String invoiceNumber, String transactionType, Long amount, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.serviceId = serviceId;
        this.invoiceNumber = invoiceNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.createdAt = createdAt;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public UUID getServiceId() {
        return serviceId;
    }
    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }
    public String getInvoiceNumber() {
        return invoiceNumber;
    
    }
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
