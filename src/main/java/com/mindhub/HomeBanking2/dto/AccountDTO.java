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
    private Set<TransactionDTO> transactions; // Anexo de transacciones a la cuenta

    public AccountDTO(Account account) {
        id = account.getId(); // Obtener el ID de la cuenta
        number = account.getNumber(); // Obtener el número de cuenta
        creationDate = account.getCreationDate(); // Obtener la fecha de creación de la cuenta
        balance = account.getBalance(); // Obtener el saldo de la cuenta

        // Mapear las transacciones de la cuenta a objetos TransactionDTO y recopilarlas en un conjunto (Set)
        transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    // Métodos getter para acceder a los atributos del DTO

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

//En resumen, esta clase AccountDTO toma una instancia de la clase Account como parámetro en su constructor y
// extrae información relevante de la cuenta, como su ID, número, fecha de creación, saldo y transacciones asociadas.
// Luego, las transacciones se convierten en objetos TransactionDTO y se almacenan en un conjunto (Set) dentro del DTO.
// Este objeto DTO se utiliza para enviar información sobre una cuenta bancaria a través de la API REST de la aplicación.