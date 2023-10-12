package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long ID;
    private String firstName, lastName, email;
    private Set<AccountDTO> accounts;

    public ClientDTO(Client client) {
        ID = client.getID(); // Obtener el ID del cliente
        firstName = client.getFirstName(); // Obtener el primer nombre del cliente
        lastName = client.getLastName(); // Obtener el apellido del cliente
        email = client.getEmail(); // Obtener el correo electrónico del cliente

        // Mapear las cuentas del cliente a objetos AccountDTO y recopilarlas en un conjunto (Set)
        accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    // Métodos getter para acceder a los atributos del DTO

    public long getID() {
        return ID;
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
}

//En resumen, esta clase ClientDTO toma una instancia de la clase Client como parámetro en su constructor y
// extrae información relevante del cliente, como su ID, primer nombre, apellido, correo electrónico y cuentas asociadas.
// Luego, las cuentas se convierten en objetos AccountDTO y se almacenan en un conjunto (Set) dentro del DTO.
// Este objeto DTO se utiliza para enviar información sobre un cliente a través de la API REST de la aplicación.