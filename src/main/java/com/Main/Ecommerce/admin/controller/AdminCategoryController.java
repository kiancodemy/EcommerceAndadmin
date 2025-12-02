package com.Main.Ecommerce.admin.controller;
import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.category.Category;
import com.Main.Ecommerce.category.dto.CategoryDto;
import com.Main.Ecommerce.category.service.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/admin/category")
@Tag(name = "admin-Category-handler", description = "APIs for categories by admin")
///@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryServiceImpl categoryService;
    private final ModelMapper modelMapper;

    @PostMapping("/addCategory/{name}")
    public ResponseEntity<Response> addCategory(@PathVariable("name") String name) {
        Category category=categoryService.addCategory(name);
        CategoryDto categoryDto=modelMapper.map(category,CategoryDto.class);
        return ResponseEntity.ok().body(new Response("اضافه شد",categoryDto));
    }

    @DeleteMapping("/deleteCategory/{id}")
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

    @PutMapping("/updat eCategory/{id}/{updatedName}")
    public ResponseEntity<Response> updateCategory(@PathVariable("updatedName") String updatedName,@PathVariable("id") Long id) {
        Category updatedCategories = categoryService.updateCategories(updatedName, id);
        CategoryDto CategoryDto =modelMapper.map(updatedCategories,CategoryDto.class);
        return ResponseEntity.ok().body(new Response("به روز رسانی با موفقیت انجام شد", CategoryDto));
    }
}