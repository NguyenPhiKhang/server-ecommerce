package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.models.Image;
import com.khangse616.serverecommerce.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public Optional<Image> getFile(String id) {
//        return imageRepository.findById(id).orElse(null);
        return imageRepository.findById(id);
    }
}
