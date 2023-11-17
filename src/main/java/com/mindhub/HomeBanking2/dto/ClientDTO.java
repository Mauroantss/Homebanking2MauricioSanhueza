package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long ID; // Almaceno el identificador único del cliente.

    private String firstName, lastName, email; // Guardo el nombre, apellido y correo electrónico del cliente.
    private Set<AccountDTO> accounts; // Mantengo un conjunto de objetos AccountDTO que representan las cuentas asociadas al cliente.
    private Set<ClientLoanDTO> loans; // Mantengo un conjunto de objetos ClientLoanDTO que representan los préstamos asociados al cliente.
    private Set<CardDTO> cards; // Mantengo un conjunto de objetos CardDTO que representan las tarjetas asociadas al cliente.

    // Constructor que toma una instancia de la clase Client y utiliza sus datos para inicializar los campos de esta clase ClientDTO.
    public ClientDTO(Client client) {
        ID = client.getId(); // Obtengo el ID del cliente a partir de la instancia de Client proporcionada.
        firstName = client.getFirstName(); // Obtengo el primer nombre del cliente.
        lastName = client.getLastName(); // Obtengo el apellido del cliente.
        email = client.getEmail(); // Obtengo el correo electrónico del cliente.

        // Filtrar y mapear las cuentas del cliente a objetos AccountDTO y almacenarlos en el conjunto accounts.
        accounts = client.getAccounts().stream()
                .filter(account -> !account.getDeleted()) // Filtrar cuentas no eliminadas.
                .map(account -> new AccountDTO(account)) // Mapear cuentas a objetos AccountDTO.
                .collect(Collectors.toSet()); // Almacenar los objetos AccountDTO en un conjunto.

        // Filtrar y mapear los préstamos del cliente a objetos ClientLoanDTO y almacenarlos en el conjunto loans.
        loans = client.getClientLoans().stream()
                .filter(clientLoan -> !clientLoan.isPaid()) // Filtrar préstamos no pagados.
                .map(loan -> new ClientLoanDTO(loan)) // Mapear préstamos a objetos ClientLoanDTO.
                .collect(Collectors.toSet()); // Almacenar los objetos ClientLoanDTO en un conjunto.

        // Filtrar y mapear las tarjetas del cliente a objetos CardDTO y almacenarlos en el conjunto cards.
        cards = client.getCards().stream()
                .filter(card -> !card.getDeleted()) // Filtrar tarjetas no eliminadas.
                .map(CardDTO::new) // Mapear tarjetas a objetos CardDTO.
                .collect(Collectors.toSet()); // Almacenar los objetos CardDTO en un conjunto.
    }

    // Métodos "get" para acceder a los valores de los campos.

    public long getID() {
        return ID; // Devuelvo el ID del cliente.
    }

    public String getFirstName() {
        return firstName; // Devuelvo el primer nombre del cliente.
    }

    public String getLastName() {
        return lastName; // Devuelvo el apellido del cliente.
    }

    public String getEmail() {
        return email; // Devuelvo el correo electrónico del cliente.
    }

    public Set<AccountDTO> getAccounts() {
        return accounts; // Devuelvo el conjunto de objetos AccountDTO representando las cuentas asociadas al cliente.
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans; // Devuelvo el conjunto de objetos ClientLoanDTO representando los préstamos asociados al cliente.
    }

    public Set<CardDTO> getCards() {
        return cards; // Devuelvo el conjunto de objetos CardDTO representando las tarjetas asociadas al cliente.
    }
}


//En resumen, esta clase ClientDTO toma una instancia de la clase Client como parámetro en su constructor y
// extrae información relevante del cliente, como su ID, primer nombre, apellido, correo electrónico y cuentas asociadas.
// Luego, las cuentas se convierten en objetos AccountDTO y se almacenan en un conjunto (Set) dentro del DTO.
// Este objeto DTO se utiliza para enviar información sobre un cliente a través de la API REST de la aplicación.