package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Loan;

import java.util.List;

// Estoy creando una clase Java llamada LoanDTO para representar un tipo de préstamo como transferencia de datos (DTO).

public class LoanDTO {
    private Long id;  // Identificador único del tipo de préstamo.
    private String name;  // Nombre del tipo de préstamo.
    private Double maxAmount;  // Monto máximo permitido para el préstamo.
    private List<Integer> payments;  // Lista de números de pagos permitidos para el préstamo.
    private Double interestPercentage;  // Porcentaje de interés asociado al préstamo.

    // Constructor que recibe un objeto Loan y mapea sus propiedades a las propiedades de LoanDTO.
    public LoanDTO(Loan loan) {
        this.id = loan.getId();  // Asigno el ID del tipo de préstamo.
        this.name = loan.getName();  // Asigno el nombre del tipo de préstamo.
        this.maxAmount = loan.getMaxAmount();  // Asigno el monto máximo permitido para el préstamo.
        this.payments =  loan.getPayments();  // Asigno la lista de números de pagos permitidos para el préstamo.
        this.interestPercentage = loan.getInterestPercentage();  // Asigno el porcentaje de interés asociado al préstamo.
    }

    // Métodos getter para acceder a las propiedades del tipo de préstamo.

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public Double getInterestPercentage() {
        return interestPercentage;
    }
}


