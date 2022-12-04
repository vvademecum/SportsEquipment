package com.example.Acrobatum.repositories;

import com.example.Acrobatum.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByProductId(Long productId);
}
