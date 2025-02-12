package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.enums.TransactionType;
import com.stgsporting.piehmecup.exceptions.InsufficientCoinsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    private final UserService userService;
    private final TransactionService transactionService;

    public WalletService(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @Transactional
    public void debit(User user, Integer amount) {
        debit(user, amount, null);
    }

    @Transactional
    public void debit(User user, Integer amount, String description) {
        if (user.getCoins() < amount) {
            throw new InsufficientCoinsException("Not enough coins");
        }

        user.setCoins(user.getCoins() - amount);

        userService.save(user);

        transactionService.makeTransaction(user, amount, TransactionType.DEBIT, description);
    }

    @Transactional
    public void credit(User user, Integer amount) {
        credit(user, amount, null);
    }

    @Transactional
    public void credit(User user, Integer amount, String description) {
        user.setCoins(user.getCoins() + amount);

        userService.save(user);

        transactionService.makeTransaction(user, amount, TransactionType.CREDIT, description);
    }
}
