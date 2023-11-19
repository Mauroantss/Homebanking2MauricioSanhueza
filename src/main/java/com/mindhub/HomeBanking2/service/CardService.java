package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.CardColor;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;

import java.util.List;
import java.util.Set;

// Esta interfaz define los servicios que pueden realizarse en relación con las tarjetas.

public interface CardService {

    // Verifica si una tarjeta con el tipo, color, cliente y estado activo especificados ya existe.
    boolean existsCardByTypeAndColorAndClientAndActive(CardType type, CardColor color, Client client, boolean active);

    // Guarda una tarjeta en el sistema.
    void saveCard(Card card);

    // Verifica si una tarjeta con el número especificado ya existe.
    boolean existsCardByNumber(String number);

    // Encuentra una tarjeta por su identificador único.
    Card findById(Long id);

    // Recupera una lista de todas las tarjetas.
    List<Card> findAll();
}


