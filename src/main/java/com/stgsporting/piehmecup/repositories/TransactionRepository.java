package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
