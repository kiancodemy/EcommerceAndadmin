package com.Main.Ecommerce.admin.controller;
import com.Main.Ecommerce.category.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryServiceImpl categoryService;

    @PostMapping("/addCategory/{name}")
    public void addCategory(@PathVariable("name") String name) {
        categoryService.addCategory(name);
    }

    @DeleteMapping("/addCategory/{id}")
    public void addCategory(@PathVariable("id") Long id) {

        categoryService.deleteCategory(id);
    }
}
