package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.IconDTO;
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
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private IconRepository iconRepository;

    public void selectIcon(Long iconId) {
        try {
            Long userId = userService.getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Icon icon = iconRepository.findIconById(iconId)
                    .orElseThrow(() -> new IconNotFoundException("Icon not found"));

            if (user.getIcons().contains(icon)) {
                user.setSelectedIcon(icon);
                userRepository.save(user);
            } else
                throw new UnownedIconException("Icon selected is not owned by the user");

        } catch (UserNotFoundException | IconNotFoundException | UnownedIconException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while selecting icon");
        }
    }

    public IconDTO getSelectedIcon() {
        try {
            Long userId = userService.getAuthenticatableId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Icon icon = user.getSelectedIcon();

            if (icon == null)
                throw new IconNotFoundException("No selected icon found");

            return IconService.iconToDTO(icon);
        } catch (UserNotFoundException | IconNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while selecting icon");
        }
    }
}
