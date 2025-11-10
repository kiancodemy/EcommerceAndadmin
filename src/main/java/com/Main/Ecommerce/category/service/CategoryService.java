package com.Main.Ecommerce.category.service;

import com.Main.Ecommerce.category.Category;

import java.util.List;

public interface CategoryService  {
    void addCategory(String name);

    void deleteCategory(Long id);
    List<Category> allCategories();
}
