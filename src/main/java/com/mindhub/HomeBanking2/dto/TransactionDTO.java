package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Transaction;
import com.mindhub.HomeBanking2.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long ID; // Almacena el identificador único de la transacción.
    private TransactionType type; // Almacena el tipo de transacción (por ejemplo, débito o crédito).
    private double amount; // Almacena el monto de la transacción.
    private LocalDateTime date; // Almacena la fecha y hora de la transacción.
    private String description; // Almacena una descripción de la transacción.
    private Double balanceAfterTransaction; // Almacena el saldo después de la transacción.

    // Constructor que toma una instancia de la clase Transaction y utiliza sus datos para inicializar los campos de esta clase TransactionDTO.
    public TransactionDTO(Transaction transaction){
        ID = transaction.getID(); // Obtengo el ID de la transacción desde la instancia de Transaction proporcionada.
        type = transaction.getType(); // Obtengo el tipo de transacción desde la instancia de Transaction.
        amount = transaction.getAmount(); // Obtengo el monto de la transacción desde la instancia de Transaction.
        date = transaction.getDate(); // Obtengo la fecha y hora de la transacción desde la instancia de Transaction.
        description = transaction.getDescription(); // Obtengo la descripción de la transacción desde la instancia de Transaction.
        balanceAfterTransaction = transaction.getBalanceAfterTransaction(); // Obtengo el saldo después de la transacción desde la instancia de Transaction.
    }

    // Métodos "get" para acceder a los valores de los campos.

    public Double getBalanceAfterTransaction() {
        return balanceAfterTransaction; // Devuelve el saldo después de la transacción.
    }

    public Long getID() {
        return ID; // Devuelve el ID de la transacción.
    }

    public TransactionType getType() {
        return type; // Devuelve el tipo de transacción.
    }

    public double getAmount() {
        return amount; // Devuelve el monto de la transacción.
    }

    public LocalDateTime getDate() {
        return date; // Devuelve la fecha y hora de la transacción.
    }

    public String getDescription() {
        return description; // Devuelve la descripción de la transacción.
    }
}




//En resumen, esta clase TransactionDTO toma una instancia de la clase Transaction como parámetro en su constructor
//y extrae información relevante de la transacción, como su ID, tipo (CRÉDITO o DÉBITO), monto, fecha y hora,
// y descripción. Este objeto DTO se utiliza para enviar información sobre una transacción a través de la API REST de la aplicación.