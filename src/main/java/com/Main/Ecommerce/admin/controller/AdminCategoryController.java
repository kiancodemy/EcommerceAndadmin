package com.Main.Ecommerce.admin.controller;
import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.category.Category;
import com.Main.Ecommerce.category.dto.CategoryDto;
import com.Main.Ecommerce.category.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/admin/category")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryServiceImpl categoryService;
    private final ModelMapper modelMapper;

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

    @GetMapping("/allcategory")
    public ResponseEntity<Response> allCategory(){
        List<Category> allCategories=categoryService.allCategories();
        List<CategoryDto> allCategoryDto =allCategories.stream().map(category -> modelMapper.map(category,CategoryDto.class)).toList();
        return ResponseEntity.ok().body(new Response("با موفقیت انجام شد", allCategoryDto));
    }
}
