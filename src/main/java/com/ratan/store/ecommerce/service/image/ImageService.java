package com.ratan.store.ecommerce.service.image;

import com.ratan.store.ecommerce.dto.ImageDTO;
import com.ratan.store.ecommerce.exceptions.ResourceNotFoundException;
import com.ratan.store.ecommerce.model.Image;
import com.ratan.store.ecommerce.model.Product;
import com.ratan.store.ecommerce.repository.ImageRepository;
import com.ratan.store.ecommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IProductService productService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("No image found with id: "+id)
        );
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                () -> {throw new ResourceNotFoundException("No image found with id: "+id);});
    }

    @Override
    public List<ImageDTO> saveImages(List<MultipartFile> image, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDTO> savedImagseDTOList = new ArrayList<>();

        for (MultipartFile file : image) {
            try {
                Image img = new Image();
                img.setFileName(file.getOriginalFilename());
                img.setFileType(file.getContentType());
                img.setImage(new SerialBlob(file.getBytes()));
                img.setProduct(product);

                String baseURL = "/api/v1/images/download/";

                String downloadUrl = baseURL+img.getId();
                img.setDownloadUrl(downloadUrl);
                Image savedImg = imageRepository.save(img);
                savedImg.setDownloadUrl(baseURL+savedImg.getId());
                imageRepository.save(savedImg);

                ImageDTO dto = new ImageDTO();
                dto.setImageId(savedImg.getId());
                dto.setImageName(savedImg.getFileName());
                dto.setDownloadUrl(savedImg.getDownloadUrl());
                savedImagseDTOList.add(dto);

            }catch (IOException | SQLException e){
                throw new  ResourceNotFoundException(e.getMessage());
            }
        }
        return savedImagseDTOList;
    }

    @Override
    public void updateImage(MultipartFile image, Long id) {
        Image imageEntity = getImageById(id);
        try{
            //imageEntity.setFileName(image.getOriginalFilename());
            imageEntity.setFileName(image.getOriginalFilename());
            imageEntity.setImage(new SerialBlob(image.getBytes()));
            imageRepository.save(imageEntity);
        }catch (IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
