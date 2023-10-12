package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // Indica que esta clase es una entidad de JPA (será mapeada a una tabla en la base de datos)
public class Transaction {
    // Propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long ID; // Identificador único de la transacción

    private TransactionType type; // Tipo de transacción (CRÉDITO o DÉBITO)
    private double amount; // Monto de la transacción
    private LocalDateTime date; // Fecha y hora de la transacción
    private String description; // Descripción de la transacción

    // Relación Many-to-One con Account (muchas transacciones pueden pertenecer a una cuenta)
    @ManyToOne // Muchas transacciones a una cuenta
    private Account account; // Cuenta a la que pertenece esta transacción

    // Constructores
    public Transaction() {
    }

    public Transaction(TransactionType type, Double amount, LocalDateTime date, String description) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Métodos getter y setter para acceder y modificar los atributos de la transacción

    public Long getID() {
        return ID;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

//En resumen, la clase Transaction representa una transacción con atributos como el tipo de transacción (CRÉDITO o DÉBITO),
// el monto, la fecha y hora, y una descripción. También tiene una relación de muchos a uno con la cuenta a la que pertenece esta transacción.
// Los métodos getter y setter permiten acceder y modificar estos atributos, y la anotación
// @Entity indica que esta clase es una entidad de JPA y se mapea a una tabla en la base de datos.