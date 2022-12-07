package com.example.Acrobatum.service;

import com.example.Acrobatum.models.Image;
import com.example.Acrobatum.models.Product;
import com.example.Acrobatum.repositories.ImageRepository;
import com.example.Acrobatum.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void saveProduct(Product product, MultipartFile file) throws IOException {
        Image image;
        if (file.getSize() != 0) {
            image = toImageEntity(file, product);
            image.setPreviewImage(true);
            product.setImage(image);
        }

        productRepository.save(product);
    }

    private Image toImageEntity(MultipartFile file, Product product) throws IOException {
        Image image;
        if(product.getImage() != null)
            image = product.getImage();
        else
            image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}