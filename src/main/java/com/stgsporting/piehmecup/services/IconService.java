package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.dtos.icons.IconDTO;
import com.stgsporting.piehmecup.dtos.icons.IconUploadDTO;
import com.stgsporting.piehmecup.entities.Icon;
import com.stgsporting.piehmecup.exceptions.IconNotFoundException;
import com.stgsporting.piehmecup.repositories.IconRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class IconService {
    private final IconRepository iconRepository;
    private final FileService fileService;

    public IconService(IconRepository iconRepository, FileService fileService) {
        this.iconRepository = iconRepository;
        this.fileService = fileService;
    }

    public void createIcon(IconUploadDTO icon) {
        Icon newIcon = dtoToIcon(icon);

        validateImage(icon.getImage());

        iconRepository.save(newIcon);
    }

    private boolean isImage(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }

    private Icon dtoToIcon(IconUploadDTO icon) {
        Icon newIcon = new Icon();
        newIcon.setName(icon.getName());
        newIcon.setAvailable(icon.getAvailable());
        newIcon.setPrice(icon.getPrice());

        validateImage(icon.getImage());

        String key = fileService.uploadFile(icon.getImage(), "/icons");

        newIcon.setImgLink(key);

        return newIcon;
    }

    private void validateImage(MultipartFile image) {
        if(image != null) {
            String contentType = image.getContentType();
            if (!isImage(contentType)) {
                throw new IllegalArgumentException("Unsupported content type: " + contentType);
            }
        }
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
        iconDTO.setImageKey(icon.getImgLink());

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

        Icon updatedIcon = dtoToIcon(iconDTO);

        if(iconDTO.getImage() != null && !icon.getImgLink().equals(updatedIcon.getImgLink()))
            fileService.deleteFile(icon.getImgLink());
        else
            updatedIcon.setImgLink(icon.getImgLink());

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
