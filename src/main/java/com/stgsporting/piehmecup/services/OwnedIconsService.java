package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.IconDTO;
import com.stgsporting.piehmecup.entities.Icon;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.IconAlreadyPurchasedException;
import com.stgsporting.piehmecup.exceptions.IconNotFoundException;
import com.stgsporting.piehmecup.exceptions.InsufficientCoinsException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.IconRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnedIconsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private IconRepository iconRepository;

    public List<IconDTO> getOwnedIcons(){
        try {
            Long userId = userService.getAuthenticatableId();
            List<Icon> icons = userRepository.findIconsByUserId(userId);
            List<IconDTO> iconDTOS = new ArrayList<>();
            for(Icon icon : icons)
                iconDTOS.add(IconService.iconToDTO(icon));

            return iconDTOS;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    public void addIconToUser(Long iconId) {
        try{
            Long userId = userService.getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Icon icon = iconRepository.findById(iconId)
                    .orElseThrow(() -> new IconNotFoundException("Icon not found"));

            if (!user.getIcons().contains(icon)) {
                if(user.getCoins() < icon.getPrice())
                    throw new InsufficientCoinsException("Not enough coins to purchase icon");

                user.setCoins(user.getCoins() - icon.getPrice());
                user.getIcons().add(icon);
                userRepository.save(user);
            }
            else
                throw new IconAlreadyPurchasedException("Icon already purchased");

        } catch (UserNotFoundException | IconNotFoundException | InsufficientCoinsException | IconAlreadyPurchasedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while adding icon to user");
        }
    }

    @Transactional
    public void removeIconFromUser(Long iconId) {
        try{
            Long userId = userService.getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Icon icon = iconRepository.findById(iconId)
                    .orElseThrow(() -> new IconNotFoundException("Icon not found"));

            if (user.getIcons().contains(icon)) {
                user.setCoins(user.getCoins() + icon.getPrice());
                user.getIcons().remove(icon);
                userRepository.save(user);
            }
            else
                throw new IconNotFoundException("Icon not found");

        } catch (UserNotFoundException | IconNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting icon from user");
        }
    }
}
