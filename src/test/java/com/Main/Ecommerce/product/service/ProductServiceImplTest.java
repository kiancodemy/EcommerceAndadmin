package com.Main.Ecommerce.product.service;

import com.Main.Ecommerce.category.Category;
import com.Main.Ecommerce.category.CategoryRepository;
import com.Main.Ecommerce.product.Product;
import com.Main.Ecommerce.product.ProductRepository;
import com.Main.Ecommerce.product.dto.ProductRequest;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Optional;



import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("dev")
class ProductServiceImplTest {

    @MockitoBean
    private  ProductRepository productRepository;

    @MockitoBean
    private  CategoryRepository categoryRepository;

    @Autowired
    private ProductServiceImpl productService;

    private ProductRequest  productRequest;

    @Mock
    private Category category;

    @Spy
    private Product product;


    @BeforeEach
    public void setup() {
        productRequest=new ProductRequest(
                "Laptop",
                "Gaming laptop",
                BigDecimal.valueOf(1000),
                5,
                true,
                1L
        );}


    @Test
    void it_ShouldNot_AddProduct_when_category_is_null() {

        ///given
        given(categoryRepository.findById(anyLong())).willReturn(Optional.empty());

        /// when + then
        assertThatThrownBy(() -> productService.addProduct(productRequest)).isInstanceOf(RuntimeException.class).hasMessage("دسته بندی موجود نیست");
    }

    @Test
    void it_Should_UpdateProduct() {

        ///given
        /// when

        /// then


    }

    @Test
    void it_Should_DeActivateProduct() {

        /// given
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        /// when
        productService.deActivateProduct(2L);
        /// THEN
        then(productRepository).should().save(productArgumentCaptor.capture());
        Product capturedProduct=productArgumentCaptor.getValue();
        assertThat(capturedProduct.getIsActive()).isFalse();

    }

    @Test
    void it_Should_CreateOrUpdateProduct() {

        ///given
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        /// when
        productService.createOrUpdateProduct(product,productRequest,category);

        /// then
        then(productRepository).should().save(productArgumentCaptor.capture());
        Product captuedProduct=productArgumentCaptor.getValue();
        assertThat(captuedProduct).satisfies(productRequest -> {
            assertThat(productRequest.getName()).isEqualTo("Laptop");
            assertThat(productRequest.getDescription()).isEqualTo("Gaming laptop");
            assertThat(productRequest.getPrice()).isEqualTo(BigDecimal.valueOf(1000));
            assertThat(productRequest.getStock()).isEqualTo(5);
            assertThat(productRequest.getIsActive()).isTrue();});


    }
}