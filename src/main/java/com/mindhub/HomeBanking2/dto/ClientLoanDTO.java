package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.ClientLoan;

// Estoy creando una clase Java llamada ClientLoanDTO para representar una transferencia de datos (DTO) de una entidad ClientLoan.

public class ClientLoanDTO {
    private Long id;  // Identificador único del préstamo asociado al cliente.
    private Long loanId;  // Identificador único del tipo de préstamo.
    private String loanName;  // Nombre del tipo de préstamo.
    private double loanAmount;  // Monto del préstamo asociado al cliente.
    private int loanPayments;  // Número de pagos del préstamo asociado al cliente.
    private  Double currentAmount;
    private int currentPayments;

    // Constructor vacío por defecto.
    public ClientLoanDTO() {
    }

    // Constructor que recibe un objeto ClientLoan y mapea sus propiedades a las propiedades de ClientLoanDTO.
    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();  // Asigno el ID del préstamo asociado al cliente.
        this.loanId = clientLoan.getLoan().getId();  // Asigno el ID del tipo de préstamo.
        this.loanName = clientLoan.getLoan().getName();  // Asigno el nombre del tipo de préstamo.
        this.loanAmount = clientLoan.getAmount();  // Asigno el monto del préstamo asociado al cliente.
        this.loanPayments = clientLoan.getPayments();  // Asigno el número de pagos del préstamo asociado al cliente.
        this.currentAmount = clientLoan.getCurrentAmount();
        this.currentPayments = clientLoan.getCurrentPayments();
    }

    // Métodos getter para acceder a las propiedades del préstamo asociado al cliente.

    public Long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getLoanName() {
        return loanName;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public int getLoanPayments() {
        return loanPayments;
    }
    public Double getCurrentAmount() {
        return currentAmount;
    }

    public int getCurrentPayments() {
        return currentPayments;
    }
}



//En resumen, esta clase ClientLoanDTO toma una instancia de la clase ClientLoan como parámetro en su constructor y
// extrae información relevante del préstamo del cliente, como su ID, el ID del préstamo asociado, el nombre del préstamo,
// el monto y la cantidad de pagos. Este objeto DTO se utiliza para enviar información sobre un
// préstamo de cliente a través de la API REST de la aplicación.