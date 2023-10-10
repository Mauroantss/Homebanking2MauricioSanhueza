package com.mindhub.HomeBanking2.repositories;

import com.mindhub.HomeBanking2.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
