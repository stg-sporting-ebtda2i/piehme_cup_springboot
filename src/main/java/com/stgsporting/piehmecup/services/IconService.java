package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.icons.IconDTO;
import com.stgsporting.piehmecup.dtos.icons.IconUploadDTO;
import com.stgsporting.piehmecup.entities.Icon;
import com.stgsporting.piehmecup.exceptions.IconNotFoundException;
import com.stgsporting.piehmecup.repositories.IconRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IconService {
    @Autowired
    private IconRepository iconRepository;

    @Autowired
    private FileService fileService;

    public void createIcon(IconUploadDTO icon) {
        Icon newIcon = dtoToIcon(icon);

        iconRepository.save(newIcon);
    }

    private Icon dtoToIcon(IconUploadDTO icon) {
        Icon newIcon = new Icon();
        newIcon.setName(icon.getName());
        newIcon.setAvailable(icon.getAvailable());
        newIcon.setPrice(icon.getPrice());

        String key = fileService.uploadFile(icon.getImage());

        newIcon.setImgLink(key);

        return newIcon;
    }

    public IconDTO getIconByName(String name) {
        Optional<Icon> icon = iconRepository.findIconByName(name);
        if(icon.isPresent())
            return iconToDTO(icon.get());


        throw new IconNotFoundException("Player with name " + name + " not found");
    }

    public IconDTO iconToDTO(Icon icon) {
        IconDTO iconDTO = new IconDTO();
        iconDTO.setId(icon.getId());
        iconDTO.setName(icon.getName());
        iconDTO.setAvailable(icon.getAvailable());
        iconDTO.setPrice(icon.getPrice());
        iconDTO.setImgLink(icon.getImgLink());

        iconDTO.setImageUrl(fileService.generateSignedUrl(icon.getImgLink()));
        return iconDTO;
    }

    public void deleteIcon(String name) {
        Icon icon = iconRepository.findIconByName(name)
                .orElseThrow(() -> new IconNotFoundException("Player with name " + name + " not found"));

        fileService.deleteFile(icon.getImgLink());

        iconRepository.delete(icon);
    }

    public void updateIcon(String name, IconUploadDTO iconDTO) {
        Icon icon = iconRepository.findIconByName(name)
                .orElseThrow(() -> new IconNotFoundException("Player with name " + name + " not found"));

        fileService.deleteFile(icon.getImgLink());
        Icon updatedIcon = dtoToIcon(iconDTO);
        updatedIcon.setId(icon.getId());
        iconRepository.save(updatedIcon);
    }

    public List<IconDTO> getAllIcons() {
        List<Icon> icons = iconRepository.findAll();
        return icons.stream().map(
                this::iconToDTO
        ).toList();
    }
}
