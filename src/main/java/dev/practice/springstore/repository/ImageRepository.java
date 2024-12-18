package dev.practice.springstore.repository;

import dev.practice.springstore.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
