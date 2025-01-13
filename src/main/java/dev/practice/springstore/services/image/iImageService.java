package dev.practice.springstore.services.image;

import dev.practice.springstore.dto.ImageDTO;
import dev.practice.springstore.models.product.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface iImageService {
    Image getImageById(Long id);

    void deleteImageById(Long id);

    List<ImageDTO> saveImage(List<MultipartFile> file, Long productId);

    void updateImage(MultipartFile file, Long imageId);
}
