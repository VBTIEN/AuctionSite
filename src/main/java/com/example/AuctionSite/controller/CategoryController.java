package com.example.AuctionSite.controller;

import com.example.AuctionSite.dto.request.CategoryRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.CategoryResponse;
import com.example.AuctionSite.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;
    
    @PostMapping("/create_category")
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
            .result(categoryService.createCategory(categoryRequest))
            .build();
    }
    
    @GetMapping("/get_all_categories")
    ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
            .result(categoryService.getAllCategories())
            .build();
    }
    
    @GetMapping("/get_category_by_name/{categoryName}")
    ApiResponse<CategoryResponse> getCategoryByName(@PathVariable("categoryName") String categoryName) {
        return ApiResponse.<CategoryResponse>builder()
            .result(categoryService.getCategoryByName(categoryName))
            .build();
    }
    
    @PutMapping("/update_category/{categoryName}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable("categoryName") String categoryName, @RequestBody CategoryRequest categoryRequest) {
        return ApiResponse.<CategoryResponse>builder()
            .result(categoryService.updateCategory(categoryName, categoryRequest))
            .build();
    }
    
    @DeleteMapping("/delete_category/{categoryName}")
    ApiResponse<String> deleteCategory(@PathVariable("categoryName") String categoryName) {
        categoryService.deleteCategory(categoryName);
        return ApiResponse.<String>builder()
            .result("Category deleted")
            .build();
    }
}
