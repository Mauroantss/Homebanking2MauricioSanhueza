package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Account;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;

    private String number;
    private LocalDate creationDate;
    private double balance;

    private Set<TransactionDTO> transactions; //Anexo las transacciones
    public AccountDTO(Account account){
        id = account.getId();
        number = account.getNumber();
        creationDate = account.getCreationDate();
        balance = account.getBalance();
        transactions = account.getTransactions().stream().map(transaction->new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
