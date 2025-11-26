package com.Main.Ecommerce.product.service;
import com.Main.Ecommerce.category.Category;
import com.Main.Ecommerce.category.CategoryRepository;
import com.Main.Ecommerce.product.filter.ProductSearchFilter;
import com.Main.Ecommerce.product.Product;
import com.Main.Ecommerce.product.ProductRepository;
import com.Main.Ecommerce.product.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductSearchFilter productSearchFilter;

    /// tested
    @Override
    public void addProduct(ProductRequest productRequest) {

        Category findCategoryByID = categoryRepository.findById(productRequest.categoryId()).orElseThrow(() -> new RuntimeException("دسته بندی موجود نیست"));

        log.info("category found successfully");

        Product product = new Product();
        createOrUpdateProduct(product,productRequest,findCategoryByID);
        log.info("added successfully");

    }

    /// tested
    @Override
    public Product updateProduct(Long id, ProductRequest productRequest) {
        Category findCategoryByID = categoryRepository.findById(productRequest.categoryId()).orElseThrow(() -> new RuntimeException("ذسته بندی موجود نیست"));

        log.info("category found successfully");

        Product findProduct=productRepository.findById(id).orElseThrow(() -> new RuntimeException("محصول موجود نیست"));
        return createOrUpdateProduct(findProduct,productRequest,findCategoryByID);


    }

    /// tested
    @Override
    public void deActivateProduct(Long id) {
        Product findProduct=productRepository.findById(id).orElseThrow(() -> new RuntimeException("محصول موجود نیست"));
        findProduct.setIsActive(false);
        productRepository.save(findProduct);

        log.info("Deactivating product with id: {}", id);
    }

    /// tested
    @Override
    public Product createOrUpdateProduct(Product product, ProductRequest productRequest, Category category) {
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setStock(productRequest.stock());
        product.setPrice(productRequest.price());
        product.setCategory(category);
        product.setIsActive(productRequest.isActive());
        return productRepository.save(product);
    }


    /// user////not tested
    @Override
    public Page<Product> allProducts(int page, Sort.Direction direction, int size, String search, BigDecimal min, BigDecimal max, Long categoryId) {
        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,"price"));
        Specification<Product> productSpecification = productSearchFilter.productSearchFilter(search, min, max, categoryId);
        return productRepository.findAll(productSpecification,pageable);
    }


    @Override
    /// tested //user
    public Product findProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("محصول موجود نیست"));
    }


}
