package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.CardRepository;
import com.mindhub.HomeBanking2.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service // Anotación que indica que esta clase es un componente de servicio gestionado por Spring.
public class CardServiceImpl implements CardService {
    @Autowired // Inyección de dependencia de la interfaz CardRepository.
    private CardRepository cardRepository;

    // Implementación de métodos definidos en la interfaz CardService.

    // Método para obtener una tarjeta por su ID.
    @Override
    public Card getCardById(Long id){
        return cardRepository.findById(id).orElse(null);
    }

    // Método para verificar si existe una tarjeta por su número.
    @Override
    public boolean existsCardByNumber(String number) {
        return cardRepository.existsByNumber(number);
    }

    // Método para verificar si existe una tarjeta por su ID.
    @Override
    public boolean existsCardById(Long id) {
        return cardRepository.existsById(id);
    }

    // Método para marcar una tarjeta como eliminada.
    @Override
    public void deletedCard(Long id) {
        Card card = getCardById(id);
        card.setDeleted(true);
        saveCard(card);
    }

    // Método para guardar una tarjeta.
    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    // Método para contar tarjetas de un cliente que no estén marcadas como eliminadas.
    @Override
    public int countByClientAndIsDeleted(Client client) {
        return cardRepository.countByClientAndIsDeleted(client, false);
    }

    // Método para obtener todas las tarjetas de un cliente que no estén marcadas como eliminadas.
    @Override
    public Set<Card> findByClientAndIsDeletedFalse(Client client) {
        return cardRepository.findByClientAndIsDeletedFalse(client);
    }
}

