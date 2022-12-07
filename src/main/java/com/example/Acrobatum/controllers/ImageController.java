package com.example.Acrobatum.controllers;

import com.example.Acrobatum.models.Image;
import com.example.Acrobatum.repositories.ImageRepository;
import com.example.Acrobatum.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageByProductId(@PathVariable Long id) {
        Image image = productRepository.findById(id).get().getImage();

        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
}