package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.AccountType;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

// Estoy creando una clase Java llamada AccountDTO para representar una transferencia de datos (DTO) de una entidad Account.

public class AccountDTO {
    private long id;  // Identificador único de la cuenta.
    private String number;  // Número de la cuenta.
    private LocalDate creationDate;  // Fecha de creación de la cuenta.
    private double balance;  // Saldo actual de la cuenta.
    private Set<TransactionDTO> transactions;  // Conjunto de transacciones asociadas a la cuenta, representadas como objetos TransactionDTO.
    private boolean active;  // Estado activo o inactivo de la cuenta.
    private AccountType accountType;  // Tipo de cuenta (por ejemplo, Ahorro, Corriente).

    // Constructor vacío por defecto.
    public AccountDTO() {
    }

    // Constructor que recibe un objeto Account y mapea sus propiedades a las propiedades de AccountDTO.
    public AccountDTO(Account account) {
        this.id = account.getId();  // Asigno el ID de la cuenta.
        this.number = account.getNumber();  // Asigno el número de la cuenta.
        this.creationDate = account.getCreationDate();  // Asigno la fecha de creación de la cuenta.
        this.balance = account.getBalance();  // Asigno el saldo actual de la cuenta.

        // Mapeo las transacciones de la cuenta a objetos TransactionDTO y las almaceno en el conjunto transactions.
        this.transactions = account
                .getTransactions()
                .stream()
                .map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toSet());

        this.active = account.getActive();  // Asigno el estado activo o inactivo de la cuenta.
        this.accountType = account.getAccountType();  // Asigno el tipo de cuenta.
    }

    // Métodos getter para acceder a las propiedades de la cuenta.

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

    public boolean getActive() {
        return active;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
