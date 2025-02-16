package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.icons.IconDTO;
import com.stgsporting.piehmecup.entities.Icon;
import com.stgsporting.piehmecup.entities.User;
import com.stgsporting.piehmecup.exceptions.IconNotFoundException;
import com.stgsporting.piehmecup.exceptions.UnownedIconException;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.repositories.IconRepository;
import com.stgsporting.piehmecup.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectIconService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final IconRepository iconRepository;
    private final IconService iconService;

    public SelectIconService(UserRepository userRepository, UserService userService, IconRepository iconRepository, IconService iconService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.iconRepository = iconRepository;
        this.iconService = iconService;
    }

    public void selectIcon(Long iconId) {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Icon icon = iconRepository.findIconById(iconId)
                .orElseThrow(() -> new IconNotFoundException("Icon not found"));

        if (!user.getIcons().contains(icon))
            throw new UnownedIconException("Icon selected is not owned by the user");

        user.setSelectedIcon(icon);
        userRepository.save(user);
    }

    public IconDTO getSelectedIcon() {
        Long userId = userService.getAuthenticatableId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Icon icon = user.getSelectedIcon();

        if (icon == null)
            throw new IconNotFoundException("No selected icon found");

        return iconService.iconToDTO(icon);
    }
}
