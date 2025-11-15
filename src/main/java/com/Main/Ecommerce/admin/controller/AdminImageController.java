package com.Main.Ecommerce.admin.controller;

import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.image.Image;
import com.Main.Ecommerce.image.dto.ImageResponseDto;
import com.Main.Ecommerce.image.service.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/image")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminImageController {
        private final ImageServiceImpl  imageServiceImpl;
        private final ModelMapper modelMapper;

    @PostMapping("/addImage/{id}")
    public ResponseEntity<Response> addImage(@RequestParam("file") MultipartFile file, @PathVariable("id") Long productId) {
        Image addImage=imageServiceImpl.addImage(productId,file);
        ImageResponseDto imageDto=modelMapper.map(addImage,ImageResponseDto.class);
        return ResponseEntity.ok().body(new Response("با موقیت اضاف شد",imageDto));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteImage(@PathVariable("id") Long imageId) {
        imageServiceImpl.deleteImage(imageId);

        return ResponseEntity.ok().body(new Response("با موقیت حذف شد",null));
    }

}
