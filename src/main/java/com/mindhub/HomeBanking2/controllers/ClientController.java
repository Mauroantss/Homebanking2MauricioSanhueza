package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/clients") // Define la ruta base para todas las solicitudes en este controlador
public class ClientController {
    @Autowired // Inyección de dependencias: Spring Boot introduce ClientRepository en esta clase
    private ClientRepository clientRepository;

    // Manejar la solicitud GET para obtener todos los clientes
    @GetMapping
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll(); // Obtener todos los clientes de la base de datos
        Stream<Client> clientStream = clients.stream(); // Convertir la lista en un flujo (stream)
        Stream<ClientDTO> clientDTOStream = clientStream.map(ClientDTO::new); // Mapear cada cliente a su DTO correspondiente
        return clientDTOStream.collect(Collectors.toList()); // Convertir el flujo de DTOs en una lista y devolverla
    }

    // Manejar la solicitud GET para obtener un cliente por su ID
    @GetMapping("/{id}") // Anotación para manejar las solicitudes GET a /api/clients/{id}
    public ClientDTO getClientById(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(ClientDTO::new) // Convierte el cliente a un DTO
                .orElse(null); // Si no se encuentra, retorna null
    }
}


//En este controlador ClientController, se manejan dos rutas:
//
//GET /api/clients: Devuelve una lista de todos los clientes en forma de objetos ClientDTO.
// Estos objetos se crean mapeando los clientes originales a DTOs utilizando un flujo (Stream) y luego se recopilan en una lista.
//
//GET /api/clients/{id}: Devuelve los detalles de un cliente específico por su ID. Busca el cliente en la base de datos
// a través de su ID y lo convierte en un objeto ClientDTO si se encuentra. Si no se encuentra, devuelve null.
//
//Este controlador permite interactuar con los clientes y proporciona información sobre ellos en formato ClientDTO,
// lo que facilita la comunicación de datos a través de la API REST.