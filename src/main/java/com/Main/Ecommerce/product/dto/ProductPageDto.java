package com.Main.Ecommerce.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProductPageDto {
    List<ProductDto> content;
    long totalElements;
    int totalPages;
    int page;
    int size;
}
