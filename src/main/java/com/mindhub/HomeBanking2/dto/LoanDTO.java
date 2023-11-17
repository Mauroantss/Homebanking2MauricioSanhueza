package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Loan;

import java.util.List;

public class LoanDTO {
    private Long id; // Almacena el identificador único del préstamo.
    private String name; // Almacena el nombre del préstamo.
    private Double maxAmount; // Almacena el monto máximo del préstamo.
    private List<Integer> payments; // Almacena la lista de pagos asociados al préstamo.
    private Double interestRate; // Almacena la tasa de interés del préstamo.

    // Constructor que toma una instancia de la clase Loan y utiliza sus datos para inicializar los campos de esta clase LoanDTO.
    public LoanDTO(Loan loan) {
        this.id = loan.getID(); // Obtengo el ID del préstamo desde la instancia de Loan proporcionada.
        this.name = loan.getName(); // Obtengo el nombre del préstamo desde la instancia de Loan.
        this.maxAmount = loan.getMaxAmount(); // Obtengo el monto máximo del préstamo desde la instancia de Loan.
        this.interestRate = loan.getInterestRate(); // Obtengo la tasa de interés del préstamo desde la instancia de Loan.
        this.payments = loan.getPayments(); // Obtengo la lista de pagos desde la instancia de Loan.
    }

    // Métodos "get" para acceder a los valores de los campos.

    public Double getInterestRate() {
        return interestRate; // Devuelve la tasa de interés del préstamo.
    }

    public Long getId() {
        return id; // Devuelve el ID del préstamo.
    }

    public String getName() {
        return name; // Devuelve el nombre del préstamo.
    }

    public Double getMaxAmount() {
        return maxAmount; // Devuelve el monto máximo del préstamo.
    }

    public List<Integer> getPayments() {
        return payments; // Devuelve la lista de pagos asociados al préstamo.
    }
}

