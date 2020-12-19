package com.khangse616.serverecommerce.utils;

import com.khangse616.serverecommerce.messages.ResponseMessage;
import com.khangse616.serverecommerce.models.Image;
import com.khangse616.serverecommerce.services.ImageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

public class ImageUtil {
    public static String addressImage(String fileName) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/images/").path(fileName).toUriString();
    }

    public static String fileName(ImageService imageService, String typeImage) {
        String nameImage = "";
        do {
            nameImage = RandomStringUtils.randomAlphanumeric(20).concat(".").concat(typeImage.toLowerCase());
        } while (imageService.checkExistsId(nameImage));
        return nameImage;

//        return RandomStringUtils.randomAlphanumeric(20);
    }

    public static String fileName() {
        return RandomStringUtils.randomAlphanumeric(20);
    }

    public static ResponseEntity<ResponseMessage<Image>> uploadImage(ImageService imageService, MultipartFile file) {
        if (file != null) {
            try {
                Image image = imageService.store(file);

                String message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(message, image));
            } catch (Exception e) {
                String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage<>(message));
            }
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage<>(""));
        }
    }

    public static ResponseEntity<ResponseMessage<List<Image>>> uploadImages(ImageService imageService, List<MultipartFile> files) {
        String message = "";
        try {
            List<Image> images = imageService.stores(files);

            message = "Uploaded the " + files.size() + " file successfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(message, images));
        } catch (Exception e) {
            message = "Could not upload the file!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage<>(message));
        }
    }
}
