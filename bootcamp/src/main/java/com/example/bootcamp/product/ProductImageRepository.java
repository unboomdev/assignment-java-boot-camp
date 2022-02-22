package com.example.bootcamp.product;

import com.example.bootcamp.product.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProductId(int id);
    Optional<ProductImage> findFirstByProductId(int id);
}
