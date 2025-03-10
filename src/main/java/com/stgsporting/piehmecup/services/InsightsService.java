package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.repositories.InsightsRepository;
import com.stgsporting.piehmecup.repositories.TransactionRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class InsightsService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final InsightsRepository insightsRepository;

    public InsightsService(TransactionRepository transactionRepository, UserRepository userRepository,
                           InsightsRepository insightsRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.insightsRepository = insightsRepository;
    }

    public void getBestSellingPlayers(Long levelId) {
        insightsRepository.getBestSellingPlayers(levelId);
    }
}
