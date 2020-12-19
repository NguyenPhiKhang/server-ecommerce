package com.khangse616.serverecommerce.controllers;

import com.khangse616.serverecommerce.exceptions.ResourceNotFoundException;
import com.khangse616.serverecommerce.models.Image;
import com.khangse616.serverecommerce.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Image image = imageService.getFile(id).orElseThrow(() -> new ResourceNotFoundException("khong tim thay image id: " + id));

        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getType())).body(image.getData());
    }
}
