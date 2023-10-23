package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long ID;

    private double amount;

    private int payments;

    // --- Relacion con el cliente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    // ---- Relacion con el prestamo
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ClientLoan() {
    }

    public ClientLoan(double amount, int payments) {
        this.amount = amount;
        this.payments = payments;
    }

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

    public Client getClientLoan() {
        return client;
    }

    public void setClientLoan(Client clientLoan) {
        this.client = clientLoan;
    }


}


//En resumen, la clase ClientLoan representa un préstamo de cliente con atributos como el monto
// y el número de pagos, y tiene relaciones de muchos a uno con el cliente al que pertenece
// y el tipo de préstamo asociado. Los métodos getter y setter permiten acceder y modificar estos atributos,
// y la anotación @Entity indica que esta clase es una entidad de JPA y se mapea a una tabla en la base de datos.