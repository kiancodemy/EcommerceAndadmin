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

    @Override
    public void addCategory(String name) {
        Category findCategory = categoryRepository.findByName(name).orElseGet(() -> Category.builder().name(name).build());
        categoryRepository.save(findCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresent((c) -> categoryRepository.deleteById(c.getId()));
    }

    @Override
    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }
}
