package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity // Indica que esta clase es una entidad de JPA (será mapeada a una tabla en la base de datos)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id; // Identificador único de la cuenta

    private String number; // Número de cuenta
    private LocalDate creationDate; // Fecha de creación de la cuenta
    private double balance; // Saldo de la cuenta

    // Relación Many-to-One de Account a Client
    @ManyToOne // Muchas cuentas pertenecen a un cliente
    @JoinColumn(name = "Client_Id") // Especifica el nombre de la columna en la base de datos
    private Client client; // Cliente al que pertenece esta cuenta

    // Relación One-to-Many de Account a Transaction
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER) // Una cuenta puede tener muchas transacciones
    private Set<Transaction> transactions = new HashSet<>(); // Conjunto de transacciones asociadas a esta cuenta

    // Constructores
    public Account() {
    }

    public Account(String number, LocalDate creationDate, double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    // Métodos getter y setter para acceder y modificar los atributos de la cuenta

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

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }
}

//En resumen, la clase Account representa una cuenta bancaria con atributos como número de cuenta, fecha de creación,
// saldo y una relación con el cliente al que pertenece. También tiene una relación de uno a muchos con las transacciones
// asociadas a esa cuenta. Los métodos getter y setter permiten acceder y modificar estos atributos,
// y el método addTransaction se utiliza para agregar transacciones a la cuenta.
// Esta clase está anotada para su uso con JPA y se mapea a una tabla en la base de datos.