package com.mindhub.HomeBanking2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // Anotación que indica que esta clase es una entidad JPA.
public class Loan {
    @Id // Anotación que especifica que este campo es la clave primaria de la entidad.
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // Generación automática del valor de la clave primaria.
    @GenericGenerator(name = "native", strategy = "native") // Generador de valores para la clave primaria.
    private Long ID; // Campo que almacena el identificador único del préstamo.

    private String name; // Campo que almacena el nombre del préstamo.
    private double maxAmount; // Campo que almacena el monto máximo del préstamo.

    private Double interestRate; // Campo que almacena la tasa de interés del préstamo.

    @ElementCollection // Anotación que indica que esta propiedad es una colección de elementos embebidos.
    private List<Integer> payments; // Campo que almacena la lista de pagos del préstamo.

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER) // Relación One-to-Many con la entidad ClientLoan.
    private Set<ClientLoan> clientLoans = new HashSet<>(); // Conjunto de préstamos de clientes asociados a este tipo de préstamo.

    // Constructor vacío por defecto.
    public Loan() {
    }

    // Constructor que recibe datos para inicializar un tipo de préstamo.
    public Loan(String name, double maxAmount, Double interestRate, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.interestRate = interestRate;
    }

    // Métodos "get" y "set" para acceder y modificar los valores de los campos del tipo de préstamo.

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Long getID() {
        return ID;
    }

    @JsonIgnore // Anotación que indica que esta propiedad debe ser ignorada al serializar a JSON (evita bucles infinitos).
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

    // Método addClientLoan() que permite agregar un préstamo de cliente a la lista asociada.
    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        this.clientLoans.add(clientLoan);
    }
}


//En resumen, la clase Loan representa un tipo de préstamo con atributos como el nombre,
// el monto máximo y una lista de pagos permitidos. También tiene una relación de uno a
// muchos con los préstamos de cliente asociados a este tipo de préstamo. Los métodos getter
// y setter permiten acceder y modificar estos atributos, y el método addClientLoan se utiliza para
// agregar préstamos de cliente a este tipo de préstamo. La anotación
// @Entity indica que esta clase es una entidad de JPA y se mapea a una tabla en la base de datos. La anotación
// @JsonIgnore se usa para evitar referencias cíclicas al serializar objetos JSON.