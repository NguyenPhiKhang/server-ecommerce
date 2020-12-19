package com.khangse616.serverecommerce.controllers;

import com.khangse616.serverecommerce.exceptions.ResourceNotFoundException;
import com.khangse616.serverecommerce.messages.ResponseMessage;
import com.khangse616.serverecommerce.models.Image;
import com.khangse616.serverecommerce.services.ImageService;
import com.khangse616.serverecommerce.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    @PostMapping("/images/upload-file")
    public ResponseEntity<ResponseMessage<Image>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = ImageUtil.fileName().concat(".").concat(Objects.requireNonNull(file.getContentType()).split("/")[1]);
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, file.getContentType(), file.getInputStream());
        return ImageUtil.uploadImage(imageService, multipartFile);
    }

    @PostMapping("/images/upload-multi-file")
    public ResponseEntity<ResponseMessage<List<Image>>> uploadFile(@RequestParam("files") MultipartFile[] files) throws IOException {
        List<MultipartFile> multipartFiles = new ArrayList<>();

        Arrays.stream(files).forEach(file -> {
            String fileName = ImageUtil.fileName().concat(".").concat(Objects.requireNonNull(file.getContentType()).split("/")[1]);
            try {
                MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, file.getContentType(), file.getInputStream());
                multipartFiles.add(multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return ImageUtil.uploadImages(imageService, multipartFiles);
    }
}
