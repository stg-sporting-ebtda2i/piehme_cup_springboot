package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.icons.IconDTO;
import com.stgsporting.piehmecup.entities.Icon;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.*;
import com.stgsporting.piehmecup.repositories.IconRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        Long userId = userService.getAuthenticatableId();
        List<Icon> icons = userRepository.findIconsByUserId(userId);
        List<IconDTO> iconDTOS = new ArrayList<>();
        for(Icon icon : icons)
            iconDTOS.add(iconService.iconToDTO(icon));

        return iconDTOS;
    }

    @Transactional
    public void addIconToUser(Long iconId) {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Icon icon = iconRepository.findById(iconId)
                .orElseThrow(() -> new IconNotFoundException("Icon not found"));

        if (user.owns(icon)) {
            throw new IconAlreadyPurchasedException("Icon already purchased");
        }

        if (! icon.getAvailable()) {
            throw new IconNotFoundException("Icon not available");
        }

        walletService.debit(user, icon.getPrice(), "Icon purchase: " + icon.getId());

        user.getIcons().add(icon);
        user.setSelectedIcon(icon);
        userRepository.save(user);
    }

    @Transactional
    public void removeIconFromUser(Long iconId) {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Icon icon = iconRepository.findById(iconId).orElseThrow(IconNotFoundException::new);

        if (icon.isDefault()) {
            throw new IllegalSellingException("You can't sell default icon");
        }

        if (!user.owns(icon)) {
            throw new UnownedIconException("User doesn't own this icon");
        }

        walletService.credit(user, icon.getPrice(), "Icon sale: " + icon.getId());

        user.getIcons().remove(icon);

        Icon defaultIcon = iconRepository.findIconByName(Icon.defaultIcon(user.getSchoolYear()))
                .orElseThrow(() -> new IconNotFoundException("Default icon not found"));

        user.setSelectedIcon(defaultIcon);
        userRepository.save(user);
    }
}
