package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.CoinsDTO;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinsService {
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserRepository userRepository;

    public void addCoins(CoinsDTO coinsDTO) {
        User user = userRepository.getUserById(coinsDTO.getUserId());
        walletService.credit(user, coinsDTO.getCoins(), coinsDTO.getDescription());
    }

    public void removeCoins(CoinsDTO coinsDTO) {
        User user = userRepository.getUserById(coinsDTO.getUserId());
        walletService.debit(user, coinsDTO.getCoins(), coinsDTO.getDescription());
    }
}
