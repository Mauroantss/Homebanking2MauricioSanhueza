package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

// Esta clase representa la entidad "ClientLoan" que se mapea a una tabla en la base de datos.

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id; // Identificador único del préstamo del cliente.

    private double amount; // Monto del préstamo.

    private int payments; // Número de pagos del préstamo.

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client; // Relación many-to-one con la entidad Client.

    @ManyToOne(fetch = FetchType.EAGER)
    private Loan loan; // Relación many-to-one con la entidad Loan.

    public ClientLoan() {
    }

    // Constructor para crear una instancia de ClientLoan con información específica.
    public ClientLoan(double amount, int payments) {
        this.amount = amount;
        this.payments = payments;
    }

    // Métodos getter y setter para acceder y modificar los atributos del préstamo del cliente.

    public Long getId() {
        return id;
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

    // Método para establecer el cliente asociado al préstamo.
    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    // Método para establecer el tipo de préstamo asociado al préstamo del cliente.
    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    // Método toString para representar el objeto como una cadena de texto.
    @Override
    public String toString() {
        return "ClientLoan{" +
                "id=" + id +
                ", amount=" + amount +
                ", payments=" + payments +
                '}';
    }
}



//En resumen, la clase ClientLoan representa un préstamo de cliente con atributos como el monto
// y el número de pagos, y tiene relaciones de muchos a uno con el cliente al que pertenece
// y el tipo de préstamo asociado. Los métodos getter y setter permiten acceder y modificar estos atributos,
// y la anotación @Entity indica que esta clase es una entidad de JPA y se mapea a una tabla en la base de datos.