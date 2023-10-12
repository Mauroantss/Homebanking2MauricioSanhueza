package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long ID;
    private TransactionType type;
    private double amount;
    private LocalDateTime date;
    private String description;

    public TransactionDTO(Transaction transaction) {
        ID = transaction.getID(); // Obtener el ID de la transacción
        type = transaction.getType(); // Obtener el tipo de transacción (CRÉDITO o DÉBITO)
        amount = transaction.getAmount(); // Obtener el monto de la transacción
        date = transaction.getDate(); // Obtener la fecha y hora de la transacción
        description = transaction.getDescription(); // Obtener la descripción de la transacción
    }

    // Métodos getter para acceder a los atributos del DTO

    public Long getID() {
        return ID;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}


//En resumen, esta clase TransactionDTO toma una instancia de la clase Transaction como parámetro en su constructor
//y extrae información relevante de la transacción, como su ID, tipo (CRÉDITO o DÉBITO), monto, fecha y hora,
// y descripción. Este objeto DTO se utiliza para enviar información sobre una transacción a través de la API REST de la aplicación.