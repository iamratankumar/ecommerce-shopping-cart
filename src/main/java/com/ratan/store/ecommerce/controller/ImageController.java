package com.ratan.store.ecommerce.controller;

import com.ratan.store.ecommerce.Response.ApiResponse;
import com.ratan.store.ecommerce.dto.ImageDTO;
import com.ratan.store.ecommerce.model.Image;
import com.ratan.store.ecommerce.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImageDTO> images = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Uploaded image successfully", images));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to save image", e.getMessage()));
        }
    }

    @GetMapping("/images/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);

        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(CONTENT_DISPOSITION,
                        "attachment; filename=\""+image.getFileName()+"\"").body(resource);

    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Image updated successfully", image));
            }
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse("Failed to update image", null)
            );
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                new ApiResponse("Failed to update image", INTERNAL_SERVER_ERROR)
        );
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Image deleted successfully", image));
            }
        }catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse("Failed to delete image", null)
            );
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                new ApiResponse("Failed to delete image", INTERNAL_SERVER_ERROR)
        );
    }
}
