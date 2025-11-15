package com.Main.Ecommerce.image.service;

import com.Main.Ecommerce.common.services.FileStorageService;
import com.Main.Ecommerce.image.Image;
import com.Main.Ecommerce.image.ImageRepository;
import com.Main.Ecommerce.product.Product;
import com.Main.Ecommerce.product.ProductRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("dev")
class ImageServiceImplTest {
     @MockitoBean
     ImageRepository imageRepository;

    @MockitoBean
     FileStorageService fileStorageService;

    @MockitoBean
    ProductRepository productRepository;

    @Autowired
    ImageServiceImpl imageServiceImpl;

    @Test
    void it_Should_DeleteImage() {

        ///given
        ArgumentCaptor<Image> imageArgumentCaptor = ArgumentCaptor.forClass(Image.class);
        given(imageRepository.findById(anyLong())).willReturn(Optional.of(Image.builder().build()));

        /// when
        imageServiceImpl.deleteImage(2L);

        /// then
        then(imageRepository).should().delete(imageArgumentCaptor.capture());
        Image  image = imageArgumentCaptor.getValue();
        assertThat(image.getProduct()).isNull();

    }

    @Test
    void it_Should_AddImage() {

        ///given
        ArgumentCaptor<Image> imageArgumentCaptor = ArgumentCaptor.forClass(Image.class);
        given(productRepository.findById(anyLong())).willReturn(Optional.of(Product.builder().build()));
        given(fileStorageService.saveFile(any(MultipartFile.class))).willReturn("a");

        /// when
        imageServiceImpl.addImage(2L,mock(MultipartFile.class));

        /// then
        then(imageRepository).should().save(imageArgumentCaptor.capture());
        Image image = imageArgumentCaptor.getValue();
        assertThat(image.getDownloadedUrl()).isEqualTo("/api/v1/image/downloadImage/a");
    }

    @Test
    void it_Should_DownloadImage() {

        ///given
        /// when

        /// then


    }
}