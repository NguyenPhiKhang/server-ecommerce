package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
