package com.Main.Ecommerce.image.service;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("dev")
class ImageServiceImplTest {

    @Test
    void it_Should_DeleteImage() {

        ///given
        /// when

        /// then


    }

    @Test
    void it_Should_AddImage() {

        ///given
        /// when

        /// then


    }

    @Test
    void it_Should_DownloadImage() {

        ///given
        /// when

        /// then


    }
}