package com.mindhub.HomeBanking2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // Indica que esta clase es una entidad de JPA (será mapeada a una tabla en la base de datos)
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long ID; // Identificador único del préstamo

    private String name; // Nombre del préstamo
    private double maxAmount; // Monto máximo del préstamo
    @ElementCollection
    private List<Integer> payments; // Lista de pagos permitidos para el préstamo

    // Relación One-to-Many con ClientLoan (un tipo de préstamo puede estar asociado a muchos préstamos de cliente)
    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>(); // Conjunto de préstamos de cliente asociados a este tipo de préstamo

    // Constructores
    public Loan() {
    }

    public Loan(String name, double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    // Métodos getter y setter para acceder y modificar los atributos del préstamo

    public Long getID() {
        return ID;
    }

    @JsonIgnore // Ignora esta propiedad al serializar objetos JSON (evita referencias cíclicas)
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    // Método para agregar un préstamo de cliente a este tipo de préstamo

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }
}

//En resumen, la clase Loan representa un tipo de préstamo con atributos como el nombre,
// el monto máximo y una lista de pagos permitidos. También tiene una relación de uno a
// muchos con los préstamos de cliente asociados a este tipo de préstamo. Los métodos getter
// y setter permiten acceder y modificar estos atributos, y el método addClientLoan se utiliza para
// agregar préstamos de cliente a este tipo de préstamo. La anotación
// @Entity indica que esta clase es una entidad de JPA y se mapea a una tabla en la base de datos. La anotación
// @JsonIgnore se usa para evitar referencias cíclicas al serializar objetos JSON.