package com.Main.Ecommerce.image.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ImageResponseDto {

    private Long id;

    private String name;

    private String downloadedUrl;

}
