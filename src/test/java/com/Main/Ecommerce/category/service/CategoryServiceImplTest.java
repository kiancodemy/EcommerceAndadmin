package com.Main.Ecommerce.category.service;
import com.Main.Ecommerce.category.Category;
import com.Main.Ecommerce.category.CategoryRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("dev")
class CategoryServiceImplTest {

    @MockitoBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    void it_Should_AddCategory() {

        ///given
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);

        given(categoryRepository.findByName(anyString())).willReturn(Optional.empty());

        /// when
        categoryServiceImpl.addCategory("kian");

        /// then
        then(categoryRepository).should().save(categoryArgumentCaptor.capture());
        Category category1 = categoryArgumentCaptor.getValue();
        assertThat(category1).isNotNull();
        assertThat(category1.getName()).isEqualTo("kian");


    }

    @Test
    void it_Should_DeleteCategory() {

        ///given
        ArgumentCaptor<Long> id = ArgumentCaptor.forClass(Long.class);
        Category category = Category.builder().id(2L).build();
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category));

        /// when
        categoryServiceImpl.deleteCategory(22L);

        /// then
        then(categoryRepository).should().deleteById(id.capture());
        Long capturedId = id.getValue();
        assertThat(capturedId).isNotNull();
        assertThat(capturedId).isEqualTo(2L);
    }

    @Test
    void it_Should_AllCategories() {

        ///given
        given(categoryRepository.findAll()).willReturn(List.of(new Category()));

        /// when
        List<Category> all = categoryServiceImpl.allCategories();

        /// then
        assertThat(all.size()).isGreaterThan(0);
    }

    @Test
    void it_Should_updateCategories() {

        ///given
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(Category.builder().name("cloths").build()));

        /// when
        categoryServiceImpl.updateCategories("shoes", 2L);

        /// then
        then(categoryRepository).should().save(categoryArgumentCaptor.capture());
        Category category1 = categoryArgumentCaptor.getValue();
        assertThat(category1).isNotNull();
        assertThat(category1.getName()).isEqualTo("shoes");
    }
}