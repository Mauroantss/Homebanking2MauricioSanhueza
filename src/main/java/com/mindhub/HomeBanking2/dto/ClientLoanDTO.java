package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.ClientLoan;

public class ClientLoanDTO {
    private Long ID; // Almaceno el identificador único del préstamo del cliente.

    private Long loanId; // Almaceno el identificador único del préstamo en sí.
    private String loanName; // Almaceno el nombre del préstamo.
    private double amount; // Almaceno el monto del préstamo.

    private int payments; // Almaceno la cantidad de pagos asociados al préstamo.

    // Constructor que toma una instancia de la clase ClientLoan y utiliza sus datos para inicializar los campos de esta clase ClientLoanDTO.
    public ClientLoanDTO(ClientLoan clientLoan) {
        ID = clientLoan.getId(); // Obtengo el ID del préstamo del cliente a partir de la instancia de ClientLoan proporcionada.
        loanId = clientLoan.getLoan().getID(); // Obtengo el ID del préstamo en sí desde la instancia de ClientLoan.
        loanName = clientLoan.getLoan().getName(); // Obtengo el nombre del préstamo desde la instancia de ClientLoan.
        amount = clientLoan.getAmount(); // Obtengo el monto del préstamo desde la instancia de ClientLoan.
        payments = clientLoan.getPayments(); // Obtengo la cantidad de pagos desde la instancia de ClientLoan.
    }

    // Métodos "get" para acceder a los valores de los campos.

    public Long getID() {
        return ID; // Devuelvo el ID del préstamo del cliente.
    }

    public Long getLoanId() {
        return loanId; // Devuelvo el ID del préstamo en sí.
    }

    public String getLoanName() {
        return loanName; // Devuelvo el nombre del préstamo.
    }

    public double getAmount() {
        return amount; // Devuelvo el monto del préstamo.
    }

    public int getPayments() {
        return payments; // Devuelvo la cantidad de pagos asociados al préstamo.
    }
}



//En resumen, esta clase ClientLoanDTO toma una instancia de la clase ClientLoan como parámetro en su constructor y
// extrae información relevante del préstamo del cliente, como su ID, el ID del préstamo asociado, el nombre del préstamo,
// el monto y la cantidad de pagos. Este objeto DTO se utiliza para enviar información sobre un
// préstamo de cliente a través de la API REST de la aplicación.