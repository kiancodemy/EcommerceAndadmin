package com.Main.Ecommerce.product;

import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.product.dto.ProductDto;
import com.Main.Ecommerce.product.dto.ProductPageDto;
import com.Main.Ecommerce.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Slf4j
@Tag(name = "Products", description = "APIs for managing products")
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Get  products by id ",
            description = "Returns just one products "
    )
    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Response> findById(@PathVariable("id") Long id) {

        Product findById = productService.findProductById(id);
        ProductDto productDto = modelMapper.map(findById, ProductDto.class);
        return ResponseEntity.ok().body(new Response("با موقیت پیدا شد", productDto));

    }


    @GetMapping("/allProduct")
    public ResponseEntity<Response> getAllProduct(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "asc", required = false) String direction,
            @RequestParam(defaultValue = "1", required = false) int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String stock,
            @RequestParam(defaultValue = "0", required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max,
            @RequestParam(required = false) Long categoryId) {
        Sort.Direction directions = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<Product> allProduct = productService.allProducts(stock, page,
                directions,
                size,
                search,
                min,
                max,
                categoryId);
        ProductPageDto productPageDto = modelMapper.map(allProduct, ProductPageDto.class);

        return ResponseEntity.ok().body(new Response("موفقیت آمیز بود", productPageDto));

    }
}
