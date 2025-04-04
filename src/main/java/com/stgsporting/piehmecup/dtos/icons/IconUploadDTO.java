package com.stgsporting.piehmecup.dtos.icons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class IconUploadDTO {
    private Long id;
    private String name;
    private MultipartFile image;
    private Integer price;
    private Boolean available;

    @Override
    public String toString() {
        return "IconUploadDTO{" +
                "id=" + id +
                ", name=" + name +
                ", image=" + image +
                ", price=" + price +
                ", available=" + available +
                '}';
    }
}
