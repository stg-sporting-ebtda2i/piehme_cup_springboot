package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.Transaction;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.enums.TransactionType;
import com.stgsporting.piehmecup.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void makeTransaction(User user, Integer amount, TransactionType type, String description) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDescription(description);
        transaction.setUser(user);
        transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        save(transaction);
    }
}
