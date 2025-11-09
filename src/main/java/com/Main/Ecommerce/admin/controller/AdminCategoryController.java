package com.Main.Ecommerce.admin.controller;
import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.category.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryServiceImpl categoryService;

    @PostMapping("/addCategory/{name}")
    public ResponseEntity<Response> addCategory(@PathVariable("name") String name) {
        categoryService.addCategory(name);
        return ResponseEntity.ok().body(new Response("اضافه شد",null));
    }

    @DeleteMapping("/addCategory/{id}")
    public ResponseEntity<Response> addCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(new Response("حذف شد",null));
    }
}
