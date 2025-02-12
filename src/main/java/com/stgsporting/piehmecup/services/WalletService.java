package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    private final UserService userService;

    public WalletService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void debit(User user, Integer amount) {
        if (user.getCoins() < amount) {
            throw new IllegalArgumentException("Not enough coins");
        }

        user.setCoins(user.getCoins() - amount);

        userService.save(user);
    }

    @Transactional
    public void credit(User user, Integer amount) {
        user.setCoins(user.getCoins() + amount);

        userService.save(user);
    }
}
