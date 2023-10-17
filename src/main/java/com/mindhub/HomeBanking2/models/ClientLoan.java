package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity // Indica que esta clase es una entidad de JPA (será mapeada a una tabla en la base de datos)
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long ID; // Identificador único del préstamo de cliente

    private String name;

    private double amount; // Monto del préstamo
    private int payments; // Número de pagos del préstamo

    // Relación Many-to-One con Client (muchos préstamos de cliente pueden pertenecer a un cliente)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id") // Nombre de la columna que almacena la clave foránea del cliente
    private Client client; // Cliente al que pertenece este préstamo de cliente

    // Relación Many-to-One con Loan (muchos préstamos de cliente pueden ser del mismo tipo de préstamo)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id") // Nombre de la columna que almacena la clave foránea del tipo de préstamo
    private Loan loan; // Tipo de préstamo asociado a este préstamo de cliente

    // Constructores
    public ClientLoan() {
    }

    public ClientLoan(double amount, int payments) {
        this.amount = amount;
        this.payments = payments;
        this.name = name;
    }

    // Métodos getter y setter para acceder y modificar los atributos del préstamo de cliente

    public Long getID() {
        return ID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}


//En resumen, la clase ClientLoan representa un préstamo de cliente con atributos como el monto
// y el número de pagos, y tiene relaciones de muchos a uno con el cliente al que pertenece
// y el tipo de préstamo asociado. Los métodos getter y setter permiten acceder y modificar estos atributos,
// y la anotación @Entity indica que esta clase es una entidad de JPA y se mapea a una tabla en la base de datos.