package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // Anotación que indica que esta clase es una entidad JPA.
public class Transaction {
    // Propiedades de la entidad Transaction.
    @Id // Anotación que especifica que este campo es la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Generación automática del valor de la clave primaria.
    @GenericGenerator(name = "native", strategy = "native") // Generador de valores para la clave primaria.
    private Long id; // Campo que almacena el identificador único de la transacción.

    private TransactionType type; // Campo que almacena el tipo de transacción (por ejemplo, débito o crédito).
    private double amount; // Campo que almacena el monto de la transacción.
    private LocalDateTime date; // Campo que almacena la fecha y hora de la transacción.
    private String description; // Campo que almacena una descripción de la transacción.
    private Double balanceAfterTransaction; // Campo que almacena el saldo después de la transacción.

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    // Campo que indica si la transacción ha sido marcada como eliminada.
    private Boolean isDeleted = false;

    // Relación Many-to-One entre Transaction y Account.
    @ManyToOne // Indica una relación Many-to-One con la entidad Account.
    private Account account; // Campo que almacena la cuenta asociada a la transacción.

    // Constructores de la clase Transaction.
    public Transaction(TransactionType debit, double amount, LocalDateTime localDateTime, String description) {
        // Constructor vacío por defecto.
    }

    public Transaction() {
        // Constructor vacío por defecto.
    }

    public Transaction(TransactionType type, Double amount, Double balanceAfterTransaction, LocalDateTime date, String description) {
        // Constructor que recibe los datos necesarios para inicializar una transacción.
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    // Métodos "get" y "set" para acceder y modificar los valores de los campos de la transacción.

    public Double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(Double balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public Long getID() {
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