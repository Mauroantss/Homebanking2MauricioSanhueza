package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String number;
    private LocalDate creationDate;
    private double balance;

    //---- Many to one de Account a Client
    @ManyToOne
    @JoinColumn(name = "Client_Id")
    private Client client;

    //---- One to many de Account a Transaction
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>(); //Le asignamos un espacio en memoria


    //---- Constructores
    public Account() {
    }

    public Account(String number, LocalDate creationDate, double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }


    // Metodos
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

    public void setAccountNumber(String accountNumber) {

    }
}


//En resumen, la clase Account representa una cuenta bancaria con atributos como número de cuenta, fecha de creación,
// saldo y una relación con el cliente al que pertenece. También tiene una relación de uno a muchos con las transacciones
// asociadas a esa cuenta. Los métodos getter y setter permiten acceder y modificar estos atributos,
// y el método addTransaction se utiliza para agregar transacciones a la cuenta.
// Esta clase está anotada para su uso con JPA y se mapea a una tabla en la base de datos.