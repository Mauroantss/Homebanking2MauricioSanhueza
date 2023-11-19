package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.CardColor;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.CardRepository;
import com.mindhub.HomeBanking2.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

// Esta clase implementa la interfaz CardService y proporciona la lógica de negocio para las operaciones relacionadas con tarjetas.

@Service
public class CardServiceImpl implements CardService {

    // Se utiliza la inyección de dependencias para acceder al repositorio de tarjetas.
    @Autowired
    private CardRepository cardRepository;

    @Override
    public boolean existsCardByTypeAndColorAndClientAndActive(CardType type,
                                                              CardColor color,
                                                              Client client, boolean active) {
        // Verifica si existe una tarjeta con el tipo, color, cliente y estado activo proporcionados.
        return cardRepository.existsByTypeAndColorAndClientAndActive(type, color, client, active);
    }

    @Override
    public void saveCard(Card card) {
        // Guarda una tarjeta en el repositorio.
        cardRepository.save(card);
    }

    @Override
    public boolean existsCardByNumber(String number) {
        // Verifica si existe una tarjeta con el número proporcionado.
        return cardRepository.existsByNumber(number);
    }

    @Override
    public Card findById(Long id) {
        // Busca una tarjeta por su ID y retorna null si no se encuentra.
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public List<Card> findAll() {
        // Retorna todas las tarjetas existentes.
        return cardRepository.findAll();
    }
}


