package com.mindhub.HomeBanking2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

// Esta clase representa la entidad "Loan" que se mapea a una tabla en la base de datos.

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id; // Identificador único del préstamo.

    private String name; // Nombre del tipo de préstamo.

    private Double maxAmount; // Monto máximo permitido para el préstamo.

    @ElementCollection
    private List<Integer> payments; // Lista de pagos asociados al préstamo.

    private Double interestPercentage; // Porcentaje de interés del préstamo.

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>(); // Relación one-to-many con la entidad ClientLoan.

    public Loan() {
    }

    // Constructor para crear una instancia de Loan con información específica.
    public Loan(String name, Double maxAmount, List<Integer> payments, Double interestPercentage) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.interestPercentage = interestPercentage;
    }

    // Métodos getter y setter para acceder y modificar los atributos del préstamo.

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Double getInterestPercentage() {
        return interestPercentage;
    }

    public void setInterestPercentage(Double interestPercentage) {
        this.interestPercentage = interestPercentage;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    // Método para agregar un préstamo de cliente asociado a este tipo de préstamo.
    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }

    // Método para obtener la lista de clientes asociados a este tipo de préstamo.
    public List<Client> getClients() {
        return clientLoans.stream().map(loans -> loans.getClient()).collect(toList());
    }

    // Método toString para representar el objeto como una cadena de texto.
    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxAmount=" + maxAmount +
                ", payments=" + payments +
                '}';
    }
}


//En resumen, la clase Loan representa un tipo de préstamo con atributos como el nombre,
// el monto máximo y una lista de pagos permitidos. También tiene una relación de uno a
// muchos con los préstamos de cliente asociados a este tipo de préstamo. Los métodos getter
// y setter permiten acceder y modificar estos atributos, y el método addClientLoan se utiliza para
// agregar préstamos de cliente a este tipo de préstamo. La anotación
// @Entity indica que esta clase es una entidad de JPA y se mapea a una tabla en la base de datos. La anotación
// @JsonIgnore se usa para evitar referencias cíclicas al serializar objetos JSON.