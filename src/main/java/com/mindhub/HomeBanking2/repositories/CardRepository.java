package com.mindhub.HomeBanking2.repositories;

import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    int countCardsByClientAndCardType(Client currentClient, CardType cardType);

}
