package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.Client;

import java.util.Set;

public interface CardService {
    // Métodos para obtener una tarjeta por su ID y verificar su existencia por número e ID.
    Card getCardById(Long id);
    boolean existsCardByNumber(String number);
    boolean existsCardById(Long id);

    // Método para marcar una tarjeta como eliminada por su ID.
    void deletedCard(Long id);

    // Método para guardar una tarjeta.
    void saveCard(Card card);

    // Método para contar tarjetas de un cliente que no están marcadas como eliminadas.
    int countByClientAndIsDeleted (Client client);

    // Método para obtener un conjunto de tarjetas de un cliente que no están marcadas como eliminadas.
    Set<Card> findByClientAndIsDeletedFalse(Client client);
}

