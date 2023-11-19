package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

// Estoy creando una clase Java llamada ClientDTO para representar una transferencia de datos (DTO) de una entidad Client.

public class ClientDTO {
    private Long id;  // Identificador único del cliente.
    private String firstName;  // Primer nombre del cliente.
    private String lastName;  // Apellido del cliente.
    private String email;  // Correo electrónico del cliente.
    private Set<AccountDTO> accounts;  // Conjunto de cuentas asociadas al cliente, representadas como objetos AccountDTO.
    private Set<ClientLoanDTO> loans;  // Conjunto de préstamos asociados al cliente, representados como objetos ClientLoanDTO.
    private Set<CardDTO> cards;  // Conjunto de tarjetas asociadas al cliente, representadas como objetos CardDTO.

    // Constructor vacío por defecto.
    public ClientDTO() {
    }

    // Constructor que recibe un objeto Client y mapea sus propiedades a las propiedades de ClientDTO.
    public ClientDTO(Client client) {
        this.id = client.getId();  // Asigno el ID del cliente.
        this.firstName = client.getFirstName();  // Asigno el primer nombre del cliente.
        this.lastName = client.getLastName();  // Asigno el apellido del cliente.
        this.email = client.getEmail();  // Asigno el correo electrónico del cliente.

        // Mapeo las cuentas activas del cliente a objetos AccountDTO y las almaceno en el conjunto accounts.
        this.accounts = client
                .getAccounts()
                .stream()
                .filter(account -> account.getActive())
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toSet());

        // Mapeo los préstamos del cliente a objetos ClientLoanDTO y los almaceno en el conjunto loans.
        this.loans = client
                .getClientLoans()
                .stream()
                .map(loan -> new ClientLoanDTO(loan))
                .collect(Collectors.toSet());

        // Mapeo las tarjetas activas del cliente a objetos CardDTO y las almaceno en el conjunto cards.
        this.cards = client
                .getCards()
                .stream()
                .filter(card -> card.getActive())
                .map(card -> new CardDTO(card))
                .collect(Collectors.toSet());
    }

    // Métodos getter para acceder a las propiedades del cliente.

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}



//En resumen, esta clase ClientDTO toma una instancia de la clase Client como parámetro en su constructor y
// extrae información relevante del cliente, como su ID, primer nombre, apellido, correo electrónico y cuentas asociadas.
// Luego, las cuentas se convierten en objetos AccountDTO y se almacenan en un conjunto (Set) dentro del DTO.
// Este objeto DTO se utiliza para enviar información sobre un cliente a través de la API REST de la aplicación.