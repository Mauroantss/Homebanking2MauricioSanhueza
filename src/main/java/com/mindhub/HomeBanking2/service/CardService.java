package com.mindhub.HomeBanking2.service;

import com.mindhub.HomeBanking2.models.Card;

public interface CardService {
    boolean existsCardByNumber(String number);

    void saveCard(Card card);
}
