package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

// Esta clase representa la entidad "Transaction" que se mapea a una tabla en la base de datos.

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id; // Identificador único de la transacción.

    private TransactionType type; // Tipo de transacción (Débito o Crédito).

    private double amount; // Monto de la transacción.

    private String description; // Descripción de la transacción.

    private LocalDateTime date; // Fecha y hora de la transacción.

    private double currentBalance; // Saldo actual después de la transacción.

    private boolean active; // Estado de la transacción (activa o no).

    @ManyToOne(fetch = FetchType.EAGER)
    private Account account; // Relación many-to-one con la entidad Account.

    public Transaction() {
    }

    // Constructor para crear una instancia de Transaction con información específica.
    public Transaction(TransactionType type, double amount, String description, LocalDateTime date, double currentBalance, boolean active) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.currentBalance = currentBalance;
        this.active = active;
    }

    // Métodos getter y setter para acceder y modificar los atributos de la transacción.

    public Long getId() {
        return id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Método toString para representar el objeto como una cadena de texto.
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}



//En resumen, la clase Transaction representa una transacción con atributos como el tipo de transacción (CRÉDITO o DÉBITO),
// el monto, la fecha y hora, y una descripción. También tiene una relación de muchos a uno con la cuenta a la que pertenece esta transacción.
// Los métodos getter y setter permiten acceder y modificar estos atributos, y la anotación
// @Entity indica que esta clase es una entidad de JPA y se mapea a una tabla en la base de datos.