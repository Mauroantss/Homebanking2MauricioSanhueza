package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.models.TransactionType;

import java.time.LocalDateTime;

// Estoy creando una clase Java llamada TransactionDTO para representar una transacción como transferencia de datos (DTO).

public class TransactionDTO {
    private Long id;  // Identificador único de la transacción.
    private TransactionType type;  // Tipo de transacción (DEBIT o CREDIT).
    private double amount;  // Monto de la transacción.
    private String description;  // Descripción de la transacción.
    private LocalDateTime date;  // Fecha y hora de la transacción.
    private double currentBalance;  // Saldo actual después de la transacción.
    private boolean active;  // Estado de la transacción (activa o inactiva).

    // Constructor vacío por defecto.
    public TransactionDTO() {
    }

    // Constructor que recibe un objeto Transaction y mapea sus propiedades a las propiedades de TransactionDTO.
    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();  // Asigno el ID de la transacción.
        this.type = transaction.getType();  // Asigno el tipo de transacción.
        this.amount = transaction.getAmount();  // Asigno el monto de la transacción.
        this.description = transaction.getDescription();  // Asigno la descripción de la transacción.
        this.date = transaction.getDate();  // Asigno la fecha y hora de la transacción.
        this.currentBalance = transaction.getCurrentBalance();  // Asigno el saldo actual después de la transacción.
        this.active = transaction.getActive();  // Asigno el estado de la transacción.
    }

    // Métodos getter para acceder a las propiedades de la transacción.

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public boolean getActive() {
        return active;
    }
}





//En resumen, esta clase TransactionDTO toma una instancia de la clase Transaction como parámetro en su constructor
//y extrae información relevante de la transacción, como su ID, tipo (CRÉDITO o DÉBITO), monto, fecha y hora,
// y descripción. Este objeto DTO se utiliza para enviar información sobre una transacción a través de la API REST de la aplicación.