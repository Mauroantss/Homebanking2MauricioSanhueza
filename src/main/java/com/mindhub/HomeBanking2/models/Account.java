package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

// Estoy creando una clase Java llamada Account que representa una cuenta en el sistema.

@Entity  // Indica que esta clase es una entidad JPA y se mapea a una tabla en la base de datos.
public class Account {
    @Id  // Indica que el atributo es la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")  // Genera automáticamente valores para la clave primaria.
    @GenericGenerator(name = "native", strategy = "native")  // Estrategia de generación automática.
    private long id;  // Identificador único de la cuenta.

    private String number;  // Número único de la cuenta.
    private LocalDate creationDate;  // Fecha de creación de la cuenta.
    private double balance;  // Saldo actual de la cuenta.
    private boolean active;  // Estado de la cuenta (activa o inactiva).
    private AccountType accountType;  // Tipo de cuenta.

    @ManyToOne(fetch = FetchType.EAGER)  // Relación muchos a uno con la entidad Client, se carga de manera ansiosa (EAGER).
    private Client client;  // Cliente al que pertenece la cuenta.

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)  // Relación uno a muchos con la entidad Transaction, se carga de manera ansiosa (EAGER).
    private Set<Transaction> transactions = new HashSet<>();  // Conjunto de transacciones asociadas a la cuenta.

    // Constructor vacío por defecto.
    public Account() {
    }

    // Constructor que inicializa algunos atributos de la cuenta.
    public Account(String number, LocalDate creationDate, double balance, boolean active, AccountType accountType) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.active = active;
        this.accountType = accountType;
    }

    // Métodos getter y setter para acceder y modificar los atributos de la cuenta.

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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    // Método para agregar una transacción a la cuenta.
    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    // Método toString para representar la información de la cuenta en formato de cadena.
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                '}';
    }
}



//En resumen, la clase Account representa una cuenta bancaria con atributos como número de cuenta, fecha de creación,
// saldo y una relación con el cliente al que pertenece. También tiene una relación de uno a muchos con las transacciones
// asociadas a esa cuenta. Los métodos getter y setter permiten acceder y modificar estos atributos,
// y el método addTransaction se utiliza para agregar transacciones a la cuenta.
// Esta clase está anotada para su uso con JPA y se mapea a una tabla en la base de datos.