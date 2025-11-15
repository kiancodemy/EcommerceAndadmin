package com.Main.Ecommerce.image;

import com.Main.Ecommerce.image.service.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/image")

@RequiredArgsConstructor
public class ImageController {
    private final ImageServiceImpl imageService;
    @GetMapping("/downloadImage/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String id){
        Resource resource = imageService.downloadImage(id);
        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
