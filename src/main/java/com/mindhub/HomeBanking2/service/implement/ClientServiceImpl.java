package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import com.mindhub.HomeBanking2.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
// Esta clase implementa la interfaz ClientService y proporciona la lógica de negocio para las operaciones relacionadas con clientes.

@Service
public class ClientServiceImpl implements ClientService {

    // Se utiliza la inyección de dependencias para acceder al repositorio de clientes.
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> findAllClients() {
        // Retorna todos los clientes almacenados en el repositorio.
        return clientRepository.findAll();
    }

    @Override
    public Client findClientById(Long id) {
        // Retorna el cliente con el ID proporcionado, o nulo si no se encuentra.
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client findClientByEmail(String email) {
        // Retorna el cliente con el correo electrónico proporcionado.
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client) {
        // Guarda un cliente en el repositorio.
        clientRepository.save(client);
    }

    @Override
    public boolean existsClientByEmail(String email) {
        // Verifica si existe un cliente con el correo electrónico proporcionado.
        return clientRepository.existsClientByEmail(email);
    }
}


