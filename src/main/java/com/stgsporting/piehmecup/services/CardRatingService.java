package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.InsufficientCoinsException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardRatingService {
    @Autowired
    private PriceService priceService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Integer getCardRating(){
        try {
            Long id = userService.getAuthenticatableId();
            User user = userRepository.getUserById(id);
            return user.getCardRating();
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
    }

    public void updateRating(Integer delta){
        try {
            Long id = userService.getAuthenticatableId();
            User user = userRepository.getUserById(id);
            Integer deltaPrice = priceService.getPrice("Rating Price").getCoins();

            if(user.getCoins() < deltaPrice*delta)
                throw new InsufficientCoinsException("Not enough coins to purchase rating");

            user.setCoins(user.getCoins() - deltaPrice*delta);

            user.setCardRating(user.getCardRating() + delta);

            userRepository.save(user);

        } catch (InsufficientCoinsException e){
            throw e;
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
    }
}
