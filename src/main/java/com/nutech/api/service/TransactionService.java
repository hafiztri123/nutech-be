package com.nutech.api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutech.api.dto.response.GetBalanceResponse;
import com.nutech.api.dto.response.GetTransactionHistory;
import com.nutech.api.dto.response.GetTransactionResponse;
import com.nutech.api.dto.response.PaginationResponse;
import com.nutech.api.exception.InvalidAction;
import com.nutech.api.exception.ItemNotFoundException;
import com.nutech.api.model.Balance;
import com.nutech.api.model.Transaction;
import com.nutech.api.repository.BalanceRepository;
import com.nutech.api.repository.InformationRepository;
import com.nutech.api.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final InformationRepository informationRepository;
    private final BalanceRepository balanceRepository;

    public TransactionService(TransactionRepository transactionRepository, InformationRepository informationRepository,
            BalanceRepository balanceRepository) {
        this.transactionRepository = transactionRepository;
        this.informationRepository = informationRepository;
        this.balanceRepository = balanceRepository;
    }

    public GetBalanceResponse getBalance(UUID userId) {
        GetBalanceResponse resp = new GetBalanceResponse();
        Balance balance = transactionRepository.getBalance(userId);

        resp.setBalance(balance.getBalance());

        return resp;
    }

    @Transactional
    public GetBalanceResponse topupBalance(UUID userId, Long additionalBalance) {
        balanceRepository.updateBalance(userId, additionalBalance);

        String invoiceNumber = "INV-" + UUID.randomUUID();

        Transaction newTransaction = new Transaction();
        newTransaction.setUserId(userId);
        newTransaction.setServiceId(null);
        newTransaction.setInvoiceNumber(invoiceNumber);
        newTransaction.setTransactionType("TOPUP");
        newTransaction.setAmount(additionalBalance);

        transactionRepository.insertTransaction(newTransaction);

        return getBalance(userId);
    }

    @Transactional
    public GetTransactionResponse createTransaction(UUID userId, String serviceCode) {

        try {
            if (!informationRepository.isServiceExistsByCode(serviceCode)) {
                throw new ItemNotFoundException("Service atau layanan tidak ditemukan");
            }

            com.nutech.api.model.Service targetService = informationRepository.getServiceByCode(serviceCode);

            Integer rowsAffected = balanceRepository.updateBalance(userId, -targetService.getServiceTariff());

            if (rowsAffected == 0) {
                throw new InvalidAction("Saldo anda tidak mencukupi");
                
            }


            String invoiceNumber = "INV-" + UUID.randomUUID();

            Transaction newTransaction = new Transaction();
            newTransaction.setUserId(userId);
            newTransaction.setServiceId(targetService.getId());
            newTransaction.setInvoiceNumber(invoiceNumber);
            newTransaction.setTransactionType("PAYMENT");
            newTransaction.setAmount(targetService.getServiceTariff());

            transactionRepository.insertTransaction(newTransaction);

            Transaction finishedTransaction = transactionRepository.getTransactionByInvoiceNumber(invoiceNumber);


            GetTransactionResponse resp = new GetTransactionResponse();
            resp.setInvoiceNumber(finishedTransaction.getInvoiceNumber());
            resp.setTransactionType(finishedTransaction.getTransactionType());
            resp.setServiceCode(targetService.getServiceCode());
            resp.setServiceName(targetService.getServiceName());
            resp.setTotalAmount(targetService.getServiceTariff());
            resp.setCreatedOn(finishedTransaction.getCreatedAt());

            return resp;

        } catch (ItemNotFoundException ex) {
            throw ex;
        }  catch (InvalidAction ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public PaginationResponse<GetTransactionHistory> getTransactionHistory(UUID userId, Integer offset, Integer limit) {

        List<GetTransactionHistory> transactionHistory = transactionRepository.getTransactionHistoryByUserId(userId, offset, limit);



        PaginationResponse<GetTransactionHistory> resp = new PaginationResponse<GetTransactionHistory>(offset, limit, transactionHistory);

        if (offset == null) {
            resp.setOffset(0);
        }

        if (limit == null) {
            resp.setLimit(0);
        }

        return resp;
    }

}
