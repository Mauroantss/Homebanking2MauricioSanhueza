package com.mindhub.HomeBanking2.repositories;

import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.CardColor;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

// Esta interfaz define un repositorio para la entidad Card, proporcionando operaciones CRUD estándar.
// La anotación @RepositoryRestResource indica que este repositorio se expondrá como un servicio REST.

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {

    // Método que verifica si existe una tarjeta con el número proporcionado.
    boolean existsByNumber(String number);

    // Método que verifica si existe una tarjeta con el tipo, color, cliente y estado activo especificados.
    boolean existsByTypeAndColorAndClientAndActive(CardType type, CardColor color, Client client, boolean active);
}

