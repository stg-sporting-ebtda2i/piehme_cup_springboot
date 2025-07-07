package com.stgsporting.piehmecup.dtos;

import com.stgsporting.piehmecup.entities.ButtonsVisibility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ButtonsVisibilityDTO {
    private String name;
    private String role;
    private Boolean visible;

    public static ButtonsVisibilityDTO from(ButtonsVisibility buttonsVisibility) {
        ButtonsVisibilityDTO dto = new ButtonsVisibilityDTO();
        dto.setName(buttonsVisibility.getName());
        dto.setRole(buttonsVisibility.getRole() != null ? buttonsVisibility.getRole().name() : null);
        dto.setVisible(buttonsVisibility.getVisible());
        return dto;
    }
}
