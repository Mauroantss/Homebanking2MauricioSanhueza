package com.mindhub.HomeBanking2.repositories;

import com.mindhub.HomeBanking2.models.Card;
import com.mindhub.HomeBanking2.models.CardType;
import com.mindhub.HomeBanking2.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByNumber(String number);
    boolean existsByCvv(String number);
    Set<Card> findByClientAndIsDeletedFalse(Client client);
    int countByClientAndIsDeleted (Client client, boolean isDeleted);

}
