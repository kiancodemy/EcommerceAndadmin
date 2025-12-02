package com.Main.Ecommerce.product.dto;

import com.Main.Ecommerce.category.dto.CategoryDto;
import com.Main.Ecommerce.image.dto.ImageDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Data
@NoArgsConstructor
public class AllProductDto {

    private Long id;
    private String name;
    Set<ImageDto> images = new HashSet<>();
    private Integer stock;
    private CategoryDto category;
}
