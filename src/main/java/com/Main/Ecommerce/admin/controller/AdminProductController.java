package com.Main.Ecommerce.admin.controller;
import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.comment.dto.CommentDto;
import com.Main.Ecommerce.product.Product;
import com.Main.Ecommerce.product.dto.ProductDto;
import com.Main.Ecommerce.product.dto.ProductRequest;
import com.Main.Ecommerce.product.service.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/admin/product")
////@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor

public class AdminProductController {

    private final ProductServiceImpl productService;
    private final ModelMapper modelMapper;

    @PostMapping("/createProduct")
    public ResponseEntity<Response> createProduct(@Valid @RequestBody ProductRequest request){

        productService.addProduct(request);

        return ResponseEntity.ok().body(new Response("محصول ایجاد شد", null));
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductRequest request){

        Product product = productService.updateProduct(id, request);
        ProductDto productDto=modelMapper.map(product,ProductDto.class);
        return ResponseEntity.ok().body(new Response("محصول به روز رسانی شد",  productDto));
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable("id") Long id){

        productService.deActivateProduct(id);
        return ResponseEntity.ok().body(new Response("محصول غیر فعال شد", null));
    }

    @GetMapping("/allProduct")
    public ResponseEntity<Response> allProduct(){
        List<Product> allProduct=productService.allProducts();
        List<ProductDto> allProductDto=allProduct.stream().map(c->modelMapper.map(c, ProductDto.class)).toList();

        return ResponseEntity.ok().body(new Response("موفقیت آمیز بود",allProductDto));

    }
}
