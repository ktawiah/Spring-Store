package dev.practice.springstore.services.image;

import dev.practice.springstore.dto.ImageDTO;
import dev.practice.springstore.exceptions.ImageException;
import dev.practice.springstore.models.product.Image;
import dev.practice.springstore.models.product.Product;
import dev.practice.springstore.repository.ImageRepository;
import dev.practice.springstore.services.product.ProductServices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements iImageService {
    private final ImageRepository repository;
    private final ProductServices productServices;

    @Override
    public Image getImageById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ImageException("No image found with id:" + id));
    }

    @Transactional
    @Override
    public void deleteImageById(Long id) {
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {
            throw new ImageException("No image found with id:" + id);
        });
    }

    @Transactional
    @Override
    public List<ImageDTO> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productServices.getProductById(productId);
        List<ImageDTO> savedImageDto = new ArrayList<>();

        // Iterate over the files and save images
        for (MultipartFile file : files) {
            Image image = new Image();
            try {
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setProduct(product);
                image.setImage(new SerialBlob(file.getBytes()));

                // Construct the download URL before saving
                String baseUrl = "api/v1/images/image/download/";
                String downloadUrl = baseUrl + image.getId();  // This will be updated after saving

                image.setDownloadUrl(downloadUrl);

                // Save the image only once
                Image savedImage = repository.save(image);

                // After saving, set the actual download URL
                savedImage.setDownloadUrl(baseUrl + savedImage.getId());

                // Save the updated image with the correct URL
                repository.save(savedImage);

                // Prepare DTO
                ImageDTO dto = new ImageDTO();
                dto.setId(savedImage.getId());
                dto.setDownloadURL(savedImage.getDownloadUrl());
                dto.setName(savedImage.getFileName());
                savedImageDto.add(dto);

            } catch (IOException | SQLException e) {
                throw new ImageException(e.getMessage());
            }
        }

        return savedImageDto;
    }


    @Transactional
    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            repository.save(image);
        } catch (IOException | SQLException e) {
            throw new ImageException(e.getMessage());
        }
    }
}
