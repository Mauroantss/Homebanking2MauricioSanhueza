package com.mindhub.HomeBanking2.repositories;

import com.mindhub.HomeBanking2.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    }
    //La interfaz creada hereda con "extends" los metodos de JpaRepository, entre los diamantes tengo que
    // poner la clase y el tipo de dato del id