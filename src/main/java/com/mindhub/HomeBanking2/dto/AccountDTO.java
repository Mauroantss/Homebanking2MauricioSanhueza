package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.AccountType;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id; // Almaceno el identificador único de la cuenta.

    private String number; // Guardo el número de cuenta.
    private LocalDate creationDate; // Registro la fecha de creación de la cuenta.
    private double balance; // Mantengo el saldo actual de la cuenta.

    private AccountType accountType; // Almaceno el tipo de cuenta (presumiblemente una enumeración llamada AccountType).
    private Set<TransactionDTO> transactions; // Mantengo un conjunto de objetos TransactionDTO que representan las transacciones asociadas a esta cuenta.

    public AccountDTO(Account account) {
        id = account.getId(); // Obtengo el ID de la cuenta a partir de la instancia de Account proporcionada.
        number = account.getNumber(); // Obtengo el número de cuenta.
        creationDate = account.getCreationDate(); // Obtengo la fecha de creación de la cuenta.
        balance = account.getBalance(); // Obtengo el saldo actual de la cuenta.

        // Filtrar y mapear las transacciones de la cuenta a objetos TransactionDTO y almacenarlos en el conjunto transactions.
        transactions = account.getTransactions().stream()
                .filter(transaction -> !transaction.getDeleted()) // Filtrar transacciones no eliminadas.
                .map(transaction -> new TransactionDTO(transaction)) // Mapear las transacciones a objetos TransactionDTO.
                .collect(Collectors.toSet()); // Almacenar los objetos TransactionDTO en un conjunto.

        accountType = account.getAccountType(); // Obtengo el tipo de cuenta desde la instancia de Account.
    }

    // Métodos "get" para acceder a los valores de los campos.

    public AccountType getAccountType() {
        return accountType; // Devuelvo el tipo de cuenta.
    }

    public long getId() {
        return id; // Devuelvo el ID de la cuenta.
    }

    public String getNumber() {
        return number; // Devuelvo el número de cuenta.
    }

    public LocalDate getCreationDate() {
        return creationDate; // Devuelvo la fecha de creación de la cuenta.
    }

    public double getBalance() {
        return balance; // Devuelvo el saldo actual de la cuenta.
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions; // Devuelvo el conjunto de objetos TransactionDTO representando las transacciones asociadas a esta cuenta.
    }
}
