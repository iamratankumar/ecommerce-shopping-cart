package com.ratan.store.ecommerce.service.image;

import com.ratan.store.ecommerce.dto.ImageDTO;
import com.ratan.store.ecommerce.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImages(List<MultipartFile> image, Long id);
    void updateImage(MultipartFile image, Long id);

}
