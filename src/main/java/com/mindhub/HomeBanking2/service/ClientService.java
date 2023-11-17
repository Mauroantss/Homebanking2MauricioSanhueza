package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Client;

import java.util.List;

public interface ClientService {
    // Método para obtener todos los clientes.
    List<Client> getAllClients();

    // Método para obtener todos los clientes en formato DTO.
    List<ClientDTO> getAllClientsDTO();

    // Método para encontrar un cliente por su ID.
    Client findClientById(Long id);

    // Método para encontrar un cliente en formato DTO por su ID.
    ClientDTO findClientDTOById(Long id);

    // Método para encontrar un cliente por su dirección de correo electrónico.
    Client findClientByEmail(String email);

    // Método para guardar un cliente.
    void saveClient(Client client);
}

