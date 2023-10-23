package com.mindhub.HomeBanking2.controllers;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController // Controlador bajo los parametros de REST(HTTP)/ le digo que esta clase va hacer el controlador.
@RequestMapping("/api/clients") // Asocio las peticiones a esta ruta(base).
public class ClientController {
    @Autowired //Injeccion de dependencias, le pedimos a Spring Boot que introduzca ClientRepository en esta clase.
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping // Servlet micro programa
    public List<ClientDTO> getAllClients() { // Esto solo es un metodo!
        List<Client> clients = clientRepository.findAll(); //Le pido al JPARepository el listado
        Stream<Client> clientStream = clients.stream();
        Stream<ClientDTO> clientDTOStream = clientStream.map(ClientDTO::new);
        return clientDTOStream.collect(Collectors.toList());
    }

    @GetMapping("/{id}") // Endpoints
    public ClientDTO getClientById(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(ClientDTO::new) // Convierte el cliente a un DTO
                .orElse(null); // Si no se encuentra, retorna null
    }

    @PostMapping
    public ResponseEntity<Object> newClient(
            @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password), false);
        clientRepository.save(client);
        return new ResponseEntity<>("Client created successfully", HttpStatus.CREATED);
    }
    @RequestMapping("/currents")
    public ClientDTO getClientCurrent(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
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