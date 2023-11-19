package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Client;

import java.util.List;

// Esta interfaz define el servicio para interactuar con la información de clientes en el sistema.

public interface ClientService {

    // Obtiene una lista de todos los clientes en el sistema.
    List<Client> findAllClients();

    // Busca un cliente por su identificador único.
    Client findClientById(Long id);

    // Busca un cliente por su dirección de correo electrónico.
    Client findClientByEmail(String email);

    // Guarda la información de un cliente en el sistema.
    void saveClient(Client client);

    // Verifica si ya existe un cliente con la dirección de correo electrónico proporcionada.
    boolean existsClientByEmail(String email);
}


