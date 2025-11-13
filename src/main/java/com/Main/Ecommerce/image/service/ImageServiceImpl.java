package com.Main.Ecommerce.image.service;

import com.Main.Ecommerce.common.services.FileStorageService;
import com.Main.Ecommerce.image.Image;
import com.Main.Ecommerce.image.ImageRepository;
import com.Main.Ecommerce.product.Product;
import com.Main.Ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final FileStorageService fileStorageService;
    private final ProductRepository productRepository;

    @Value("${image.upload.url}")
    private String uploadedUrl;

    @Override
    public void deleteImage(Long id) {
        Image image =imageRepository.findById(id).orElseThrow(()->new RuntimeException("عکس موجود نست"));
        image.setProduct(null);
        imageRepository.delete(image);

        log.info("image deleted with id {}", image.getId());
    }

    @Override
    public Image addImage(Long productId, MultipartFile file) {
        Product product=productRepository.findById(productId).orElseThrow(()->new RuntimeException("محصول یافت نشد"));
        String finalPath=fileStorageService.saveFile(file);
        String downloadedPath="/api/v1/image/downloadImage/"+finalPath;
        Image image = Image.builder().downloadedUrl(downloadedPath).product(product).build();
        return imageRepository.save(image);
    }

    @Override
    public InputStreamSource downloadImage(String downloadedUrl) {
        return fileStorageService.downLoadImage(downloadedUrl);
    }
}
