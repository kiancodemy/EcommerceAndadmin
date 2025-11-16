package com.Main.Ecommerce.product.dto;

import com.Main.Ecommerce.category.dto.CategoryDto;
import com.Main.Ecommerce.comment.dto.CommentDto;
import com.Main.Ecommerce.image.dto.ImageDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    Set<ImageDto> images = new HashSet<>();
    private Integer stock;
    private Boolean isActive;
    private CategoryDto category;
    private Set<CommentDto> comments = new HashSet<>();
}
