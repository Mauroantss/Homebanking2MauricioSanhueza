package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import com.mindhub.HomeBanking2.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service // Anotación que indica que esta clase es un componente de servicio gestionado por Spring.
public class ClientServiceImpl implements ClientService {

    @Autowired // Inyección de dependencia de la interfaz ClientRepository.
    ClientRepository clientRepository;

    // Implementación de métodos definidos en la interfaz ClientService.

    // Método para obtener todos los clientes.
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Método para obtener todos los clientes como objetos DTO.
    @Override
    public List<ClientDTO> getAllClientsDTO() {
        // Mapea la lista de clientes a una lista de ClientDTO utilizando la función de mapeo.
        return getAllClients().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    // Método para encontrar un cliente por su ID.
    @Override
    public Client findClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    // Método para encontrar un cliente como objeto DTO por su ID.
    @Override
    public ClientDTO findClientDTOById(Long id) {
        return clientRepository.findById(id)
                .map(ClientDTO::new) // Mapea el cliente a un ClientDTO si se encuentra.
                .orElse(null);
    }

    // Método para encontrar un cliente por su dirección de correo electrónico.
    @Override
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    // Método para guardar un cliente en el repositorio.
    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}

