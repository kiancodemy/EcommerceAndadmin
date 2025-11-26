package com.Main.Ecommerce.category.repository;

import com.Main.Ecommerce.category.Category;
import com.Main.Ecommerce.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void  it_Should_FindByName() {

        ///given
        Category category = Category.builder().name("kian").build();
        categoryRepository.save(category);

        /// when
        Optional<Category> category1=categoryRepository.findByName(category.getName());

        /// then
        assertThat(category1).isPresent().hasValueSatisfying(c->{
            assertThat(c).isNotNull();
            assertThat(c.getName()).isEqualTo("kian");

        });
    }
}