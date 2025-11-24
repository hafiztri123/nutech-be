CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID REFERENCES users(id) NOT NULL,
    service_id UUID REFERENCES services(id) ON DELETE SET NULL,
    invoice_number VARCHAR(255) NOT NULL UNIQUE,
    transaction_type VARCHAR(30) NOT NULL,
    amount BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    

    
    CONSTRAINT chk_transaction_type 
        CHECK (
            (transaction_type = 'PAYMENT' AND service_id IS NOT NULL) OR
            (transaction_type = 'TOPUP' AND service_id IS NULL)
        )
);