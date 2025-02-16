package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.icons.IconDTO;
import com.stgsporting.piehmecup.entities.Icon;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.IconAlreadyPurchasedException;
import com.stgsporting.piehmecup.exceptions.IconNotFoundException;
import com.stgsporting.piehmecup.exceptions.InsufficientCoinsException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.IconRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnedIconsService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final IconRepository iconRepository;
    private final WalletService walletService;
    private final IconService iconService;

    public OwnedIconsService(UserRepository userRepository, UserService userService, IconRepository iconRepository, WalletService walletService, IconService iconService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.iconRepository = iconRepository;
        this.walletService = walletService;
        this.iconService = iconService;
    }

    public List<IconDTO> getOwnedIcons(){
        try {
            Long userId = userService.getAuthenticatableId();
            List<Icon> icons = userRepository.findIconsByUserId(userId);
            List<IconDTO> iconDTOS = new ArrayList<>();
            for(Icon icon : icons)
                iconDTOS.add(iconService.iconToDTO(icon));

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
                walletService.debit(user, icon.getPrice(), "Icon purchase: " + icon.getId());

                user.getIcons().add(icon);
                user.setSelectedIcon(icon);
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
                walletService.credit(user, icon.getPrice(), "Icon sale: " + icon.getId());

                user.getIcons().remove(icon);

                Optional<Icon> defaultIcon = iconRepository.findIconByName("Default");
                if(defaultIcon.isEmpty())
                    throw new IconNotFoundException("Default icon not found");

                user.setSelectedIcon(defaultIcon.get());
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
