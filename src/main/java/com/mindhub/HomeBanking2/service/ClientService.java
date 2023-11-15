package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.dto.ClientDTO;
import com.mindhub.HomeBanking2.models.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();
    List<ClientDTO> getAllClientsDTO();
    Client findClientById(Long id);

    ClientDTO findClientDTOById(Long id);
    Client findClientByEmail(String email);

    void saveClient(Client client);


}
