package com.Main.Ecommerce.category.service;
import com.Main.Ecommerce.category.Category;
import com.Main.Ecommerce.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    //tested
    @Override
    public Category addCategory(String name) {
        Category findCategory = categoryRepository.findByName(name).orElseGet(() -> Category.builder().name(name).build());
        return categoryRepository.save(findCategory);
    }

    //tested
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresent((c) -> categoryRepository.deleteById(c.getId()));
    }

    //tested
    @Override
    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }
}
