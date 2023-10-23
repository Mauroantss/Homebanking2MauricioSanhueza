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

    public TransactionDTO(Transaction transaction){
        ID = transaction.getID();
        type = transaction.getType();
        amount = transaction.getAmount();
        date = transaction.getDate();
        description = transaction.getDescription();
    }

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