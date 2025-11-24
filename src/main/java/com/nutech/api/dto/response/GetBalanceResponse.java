package com.nutech.api.dto.response;

public class GetBalanceResponse {
    private Long balance;


    public GetBalanceResponse() {}
    public GetBalanceResponse(Long balance) {
        this.balance = balance;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
    
}
