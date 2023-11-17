package com.mindhub.HomeBanking2.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity // Anotación que indica que esta clase es una entidad JPA.
public class ClientLoan {
    @Id // Anotación que especifica que este campo es la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Generación automática del valor de la clave primaria.
    @GenericGenerator(name = "native", strategy = "native") // Generador de valores para la clave primaria.
    private Long id; // Campo que almacena el identificador único del préstamo del cliente.

    private double amount; // Campo que almacena el monto del préstamo.
    private int payments; // Campo que almacena la cantidad de pagos del préstamo.
    private Boolean isPaid; // Campo que indica si el préstamo ha sido pagado o no.

    // Relación Many-to-One entre ClientLoan y Client, indicando que un préstamo del cliente pertenece a un cliente.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    // Relación Many-to-One entre ClientLoan y Loan, indicando que un préstamo del cliente se relaciona con un tipo de préstamo.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    // Constructor vacío por defecto.
    public ClientLoan() {
    }

    // Constructor que recibe datos para inicializar un préstamo del cliente.
    public ClientLoan(double amount, int payments, boolean isPaid) {
        this.amount = amount;
        this.payments = payments;
        this.isPaid = isPaid;
    }

    // Métodos "get" y "set" para acceder y modificar los valores de los campos del préstamo del cliente.

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

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

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    // Método adicional getClientLoan() que devuelve el cliente asociado al préstamo del cliente.
    public Client getClientLoan() {
        return client;
    }
}



//En resumen, la clase ClientLoan representa un préstamo de cliente con atributos como el monto
// y el número de pagos, y tiene relaciones de muchos a uno con el cliente al que pertenece
// y el tipo de préstamo asociado. Los métodos getter y setter permiten acceder y modificar estos atributos,
// y la anotación @Entity indica que esta clase es una entidad de JPA y se mapea a una tabla en la base de datos.