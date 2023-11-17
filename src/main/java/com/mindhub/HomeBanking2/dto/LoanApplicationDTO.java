package com.mindhub.HomeBanking2.dto;

public class LoanApplicationDTO {
    private Long loanId; // Almacena el identificador único del préstamo al que se solicita la aplicación.
    private double amount; // Almacena el monto del préstamo solicitado.
    private int payments; // Almacena la cantidad de pagos asociados al préstamo.
    private String toAccount; // Almacena el número de cuenta de destino donde se transferirá el monto del préstamo.

    // Constructor que recibe los datos de solicitud de préstamo al ser instanciado.
    public LoanApplicationDTO(Long loanId, double amount, int payments, String toAccount) {
        this.loanId = loanId; // Asigna el ID del préstamo.
        this.amount = amount; // Asigna el monto del préstamo.
        this.payments = payments; // Asigna la cantidad de pagos.
        this.toAccount = toAccount; // Asigna el número de cuenta de destino.
    }

    // Métodos "get" para acceder a los valores de los campos.

    public Long getLoanId() {
        return loanId; // Devuelve el ID del préstamo.
    }

    public double getAmount() {
        return amount; // Devuelve el monto del préstamo.
    }

    public int getPayments() {
        return payments; // Devuelve la cantidad de pagos asociados al préstamo.
    }

    public String getToAccount() {
        return toAccount; // Devuelve el número de cuenta de destino.
    }
}

