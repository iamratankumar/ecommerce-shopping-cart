package com.ratan.store.ecommerce.controller;

import com.ratan.store.ecommerce.Response.ApiResponse;
import com.ratan.store.ecommerce.exceptions.AlreadyExistsException;
import com.ratan.store.ecommerce.exceptions.ResourceNotFoundException;
import com.ratan.store.ecommerce.model.Category;
import com.ratan.store.ecommerce.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categoryList = categoryService.findAllCategories();
           return  ResponseEntity.ok(new ApiResponse("SUCCESS", categoryList));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR", e.getMessage()));
        }
    }

    @GetMapping("/get/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return  ResponseEntity.ok(new ApiResponse("SUCCESS", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }
    }

    @GetMapping("/get/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category category = categoryService.getCategoryByName(name);
            return  ResponseEntity.ok(new ApiResponse("SUCCESS", category));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(Category category) {
        try {
            Category categorySaved = categoryService.addCategory(category);
            return  ResponseEntity.ok(new ApiResponse("SUCCESS", categorySaved));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return  ResponseEntity.ok(new ApiResponse("SUCCESS", null));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, Category category) {
        try {
            Category updateCategory = categoryService.updateCategory(category, id);
            return  ResponseEntity.ok(new ApiResponse("SUCCESS", updateCategory));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }



}
