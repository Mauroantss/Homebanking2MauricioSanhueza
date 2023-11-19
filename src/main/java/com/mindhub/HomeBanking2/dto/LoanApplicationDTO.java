package com.mindhub.HomeBanking2.dto;

// Estoy creando una clase Java llamada LoanApplicationDTO para representar una solicitud de préstamo como transferencia de datos (DTO).

public class LoanApplicationDTO {
    private long loanId;  // Identificador único del tipo de préstamo solicitado.
    private double amount;  // Monto solicitado en la solicitud de préstamo.
    private int payments;  // Número de pagos solicitados en la solicitud de préstamo.
    private String destinationAccount;  // Número de cuenta de destino en la solicitud de préstamo.

    // Constructor vacío por defecto.
    public LoanApplicationDTO() {
    }

    // Constructor que recibe parámetros para inicializar las propiedades de la solicitud de préstamo.
    public LoanApplicationDTO(long loanId, double amount, int payments, String destinationAccount) {
        this.loanId = loanId;  // Asigno el ID del tipo de préstamo.
        this.amount = amount;  // Asigno el monto solicitado.
        this.payments = payments;  // Asigno el número de pagos solicitados.
        this.destinationAccount = destinationAccount;  // Asigno el número de cuenta de destino.
    }

    // Métodos getter para acceder a las propiedades de la solicitud de préstamo.

    public long getLoanId() {
        return loanId;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }
}


