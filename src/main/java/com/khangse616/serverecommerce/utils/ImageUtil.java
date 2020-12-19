package com.khangse616.serverecommerce.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ImageUtil {
    public static String addressImage(String fileName) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/images/").path(fileName).toUriString();
    }
}
