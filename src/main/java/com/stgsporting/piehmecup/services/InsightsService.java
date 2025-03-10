package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.insights.BestSellerDTO;
import com.stgsporting.piehmecup.repositories.InsightsRepository;
import com.stgsporting.piehmecup.repositories.TransactionRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<BestSellerDTO> findBestSeller(Long levelId) {
        return insightsRepository.findBestSeller(levelId);
    }
}
