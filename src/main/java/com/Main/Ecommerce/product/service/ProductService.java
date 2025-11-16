package com.Main.Ecommerce.product.service;
import com.Main.Ecommerce.category.Category;
import com.Main.Ecommerce.product.Product;
import com.Main.Ecommerce.product.dto.ProductRequest;

import java.util.List;

public interface ProductService {
    void addProduct(ProductRequest  productRequest);
    Product updateProduct(Long id , ProductRequest  productRequest);
    void deActivateProduct(Long id);

    Product createOrUpdateProduct(Product product, ProductRequest productRequest, Category category);

    List<Product> allProducts();
}
