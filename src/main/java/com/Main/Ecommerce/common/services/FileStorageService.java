package com.Main.Ecommerce.common.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    @Value("${image.upload.url}")
    private static String StorageDirectory;

    public String saveFile(MultipartFile file) {

        long maxSize = 2 * 1024 * 1024; // 2 M
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("فایل الزامی است");
        }
        else if (file.getSize() > maxSize) {
            throw new RuntimeException("سایز فایل باید کمتر از دو مگابایت باشد");
        }

        String FinalName= UUID.randomUUID() + file.getOriginalFilename();
        var fileTarget=new File(StorageDirectory+File.separator,FinalName);
        if(!Objects.equals(fileTarget.getParent(),StorageDirectory)){
            throw new SecurityException("خطا رخ داده");

        }
        try {
            Files.copy(file.getInputStream(), fileTarget.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return FinalName;
        } catch (IOException e) {
            log.info("IOException during copying file");
            throw new RuntimeException(e.getMessage());
        }}


    public Resource downLoadImage(String imageName) {
        var url=new File(StorageDirectory+File.separator, imageName);
        if(!url.exists()){
            throw new RuntimeException("فایل موجود نیست");

        }
        if(Objects.equals(url.getPath(),StorageDirectory)){
            throw new RuntimeException("خطا رخ داده");
        }
        try {
           return new InputStreamResource(Files.newInputStream(url.toPath()));
        } catch (IOException e) {
            log.info("IOException during downloading image");
            throw new RuntimeException("خطا در ذخیره سازی");
        }
    }
}
