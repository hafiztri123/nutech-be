package com.nutech.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nutech.api.dto.request.PaginationRequest;
import com.nutech.api.dto.request.TopupRequest;
import com.nutech.api.dto.request.TransactionRequest;
import com.nutech.api.dto.response.GenericResponse;
import com.nutech.api.dto.response.GetBalanceResponse;
import com.nutech.api.dto.response.GetTransactionHistory;
import com.nutech.api.dto.response.GetTransactionResponse;
import com.nutech.api.dto.response.PaginationResponse;
import com.nutech.api.model.User;
import com.nutech.api.service.TransactionService;

import jakarta.validation.Valid;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/balance")
    public ResponseEntity<GenericResponse<GetBalanceResponse>> getBalance(
            @AuthenticationPrincipal User user) {

        GetBalanceResponse resp = transactionService.getBalance(user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(
                new GenericResponse<GetBalanceResponse>(0, "Get Balance Berhasil", resp));

    }

        @PostMapping("/topup")
    public ResponseEntity<GenericResponse<GetBalanceResponse>> topupBalance(
        @AuthenticationPrincipal User user,
        @RequestBody @Valid TopupRequest request
     ) {

        GetBalanceResponse resp = transactionService.topupBalance(user.getId(), request.getTopupAmount());

        return ResponseEntity.status(HttpStatus.OK).body(
                new GenericResponse<GetBalanceResponse>(0, "Topup Balance Berhasil", resp));

     }


     @PostMapping("/transaction")
     public ResponseEntity<GenericResponse<GetTransactionResponse>> transaction(
        @AuthenticationPrincipal User user,
        @RequestBody @Valid TransactionRequest request
     ) {

        GetTransactionResponse resp = transactionService.createTransaction(user.getId(), request.getServiceCode());

        return ResponseEntity.status(HttpStatus.OK).body(
                new GenericResponse<GetTransactionResponse>(0, "Transaksi berhasil", resp));
     }

     @GetMapping("/transaction/history") 
     public ResponseEntity<GenericResponse<PaginationResponse<GetTransactionHistory>>> getTransactionHistory(
         @AuthenticationPrincipal User user,
         @ModelAttribute PaginationRequest request
     ) {

        PaginationResponse<GetTransactionHistory> resp = transactionService.getTransactionHistory(user.getId(), request.getOffset(), request.getLimit());

        return ResponseEntity.status(HttpStatus.OK).body(
                new GenericResponse<PaginationResponse<GetTransactionHistory>>(0, "Get Transaction History Berhasil", resp));
     }

}
