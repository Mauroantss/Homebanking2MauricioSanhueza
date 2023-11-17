package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity // Anotación que indica que esta clase es una entidad JPA.
public class Account {

    @Id // Anotación que especifica que este campo es la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Generación automática del valor de la clave primaria.
    @GenericGenerator(name = "native", strategy = "native") // Generador de valores para la clave primaria.
    private long id; // Campo que almacena el identificador único de la cuenta.

    private String number; // Campo que almacena el número de cuenta.
    private LocalDate creationDate; // Campo que almacena la fecha de creación de la cuenta.
    private double balance; // Campo que almacena el saldo de la cuenta.

    private AccountType accountType; // Campo que almacena el tipo de cuenta.

    // Campo que almacena información sobre si la cuenta ha sido eliminada o no.
    private Boolean isDeleted = false;

    // Relación Many-to-One entre Account y Client, indicando que muchas cuentas pueden pertenecer a un cliente.
    @ManyToOne
    @JoinColumn(name = "Client_Id") // Columna que se utilizará como clave foránea en la tabla.
    private Client client; // Campo que almacena la referencia al cliente propietario de la cuenta.

    // Relación One-to-Many entre Account y Transaction, indicando que una cuenta puede tener muchas transacciones.
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER) // Se mapea con el campo "account" en la clase Transaction.
    private Set<Transaction> transactions = new HashSet<>(); // Conjunto de transacciones asociadas a la cuenta.

    // Constructor vacío por defecto.
    public Account() {
    }

    // Constructor que recibe datos para inicializar una cuenta.
    public Account(String number, LocalDate creationDate, double balance, AccountType accountType) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.accountType = accountType;
    }

    // Métodos "get" y "set" para acceder y modificar los valores de los campos de la cuenta.

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    // Método para agregar una transacción a la cuenta y establecer la relación bidireccional.
    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }

    // Métodos "get" y "set" para el campo "isDeleted".
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}



//En resumen, la clase Account representa una cuenta bancaria con atributos como número de cuenta, fecha de creación,
// saldo y una relación con el cliente al que pertenece. También tiene una relación de uno a muchos con las transacciones
// asociadas a esa cuenta. Los métodos getter y setter permiten acceder y modificar estos atributos,
// y el método addTransaction se utiliza para agregar transacciones a la cuenta.
// Esta clase está anotada para su uso con JPA y se mapea a una tabla en la base de datos.