package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.CategoryRequest;
import com.example.AuctionSite.dto.response.CategoryResponse;
import com.example.AuctionSite.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest categoryRequest);

    CategoryResponse toCategoryResponse(Category category);

    void toUpdateCategory(@MappingTarget Category category, CategoryRequest categoryRequest);
}
