package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {
    private long ID;

    // Definimos las propiedades del cliente
    private String firstName, lastName, email;

    private List<AccountsDTO> accounts;

    public ClientDTO(Client client){

        ID = client.getID();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        accounts = client.getAccounts().stream().map(account -> new AccountsDTO(account)).collect(Collectors.toList());
    }

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

    public List<AccountsDTO> getAccounts() {
        return accounts;
    }
}

