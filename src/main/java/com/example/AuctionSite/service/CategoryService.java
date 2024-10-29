package com.example.AuctionSite.service;

import com.example.AuctionSite.dto.request.CategoryRequest;
import com.example.AuctionSite.dto.response.CategoryResponse;
import com.example.AuctionSite.entity.Category;
import com.example.AuctionSite.mapper.CategoryMapper;
import com.example.AuctionSite.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    
    @PreAuthorize("hasAuthority('CREATE_CATEGORY')")
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toCategory(categoryRequest);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }
    
    @PreAuthorize("hasAuthority('GET_ALL_CATEGORIES')")
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }
    
    @PreAuthorize("hasAuthority('GET_CATEGORY_BY_NAME')")
    public CategoryResponse getCategoryByName(String name) {
        Category category = categoryRepository.findById(name).orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toCategoryResponse(category);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_CATEGORY')")
    public CategoryResponse updateCategory(String name, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(name).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryMapper.toUpdateCategory(category, categoryRequest);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }
    
    @PreAuthorize("hasAuthority('DELETE_CATEGORY')")
    public void deleteCategory(String name) {
        categoryRepository.deleteById(name);
    }
}
