package com.Main.Ecommerce.product.service;
import com.Main.Ecommerce.category.Category;
import com.Main.Ecommerce.product.Product;
import com.Main.Ecommerce.product.dto.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void addProduct(ProductRequest  productRequest);
    Product updateProduct(Long id , ProductRequest  productRequest);
    void deActivateProduct(Long id);

    Product createOrUpdateProduct(Product product, ProductRequest productRequest, Category category);

    Page<Product> allProducts(int page, Sort.Direction direction,
                              int size,
                              String search,
                              BigDecimal min,
                              BigDecimal max,
                              Long categoryId);

    Product findProductById(Long id);
}
