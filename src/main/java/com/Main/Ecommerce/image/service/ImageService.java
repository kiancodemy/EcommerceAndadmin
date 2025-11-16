package com.Main.Ecommerce.image.service;

import com.Main.Ecommerce.image.Image;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    void deleteImage(Long id);
    Image addImage(Long productId, MultipartFile file);

    Resource downloadImage(String downloadedUrl);

    List<Image> ALLImage();
}
