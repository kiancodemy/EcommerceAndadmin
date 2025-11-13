package com.Main.Ecommerce.image.service;

import com.Main.Ecommerce.image.Image;
import org.springframework.core.io.InputStreamSource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void deleteImage(Long id);
    Image addImage(Long productId, MultipartFile file);

    InputStreamSource downloadImage(String downloadedUrl);
}
