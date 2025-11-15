package com.Main.Ecommerce.product.service;
import com.Main.Ecommerce.category.Category;
import com.Main.Ecommerce.category.CategoryRepository;
import com.Main.Ecommerce.product.Product;
import com.Main.Ecommerce.product.ProductRepository;
import com.Main.Ecommerce.product.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

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
}
