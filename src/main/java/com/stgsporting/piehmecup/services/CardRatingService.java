package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.UserCardDTO;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.MinRatingException;
import org.springframework.stereotype.Service;

@Service
public class CardRatingService {
    private final PriceService priceService;
    private final UserService userService;
    private final WalletService walletService;
    private final FileService fileService;
    private static final int MIN_RATING = 50;

    public CardRatingService(PriceService priceService, UserService userService, WalletService walletService, FileService fileService) {
        this.priceService = priceService;
        this.userService = userService;
        this.walletService = walletService;
        this.fileService = fileService;
    }

    public Integer getCardRating(){
        long id = userService.getAuthenticatableId();
        User user = userService.getAuthenticatableById(id);

        return user.getCardRating();
    }

    public void updateRating(Integer delta){
        long id = userService.getAuthenticatableId();
        User user = userService.getAuthenticatableById(id);

        Integer deltaPrice = priceService.getPrice("Rating Price", user.getSchoolYear().getLevel()).getCoins();

        if (user.getCardRating() + delta < MIN_RATING)
            throw new MinRatingException("Rating cannot be negative");

        walletService.debit(user, deltaPrice*delta, "Rating changed by " + delta);

        user.setCardRating(user.getCardRating() + delta);

        userService.save(user);
    }

    public UserCardDTO getUserCardDTO(){
        long id = userService.getAuthenticatableId();
        User user = userService.getAuthenticatableById(id);

        return new UserCardDTO(
                user.getId(),
                user.getUsername(),
                user.getCardRating(),
                user.getLineupRating(),
                fileService.generateSignedUrl(user.getImgLink()),
                user.getImgLink(),
                fileService.generateSignedUrl(user.getSelectedIcon().getImgLink()),
                user.getSelectedIcon().getImgLink(),
                user.getSelectedPosition().getName()
        );
    }

    public UserCardDTO getUserCardDTO(Long userId){
        User user = userService.getAuthenticatableById(userId);

        return new UserCardDTO(
                user.getId(),
                user.getUsername(),
                user.getCardRating(),
                user.getLineupRating(),
                fileService.generateSignedUrl(user.getImgLink()),
                user.getImgLink(),
                fileService.generateSignedUrl(user.getSelectedIcon().getImgLink()),
                user.getSelectedIcon().getImgLink(),
                user.getSelectedPosition().getName()
        );
    }

}
