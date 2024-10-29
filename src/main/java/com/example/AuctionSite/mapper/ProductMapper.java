package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.request.ProductRequest;
import com.example.AuctionSite.dto.response.ProductResponse;
import com.example.AuctionSite.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product toProduct(ProductRequest productRequest);
    
    ProductResponse toProductResponse(Product product);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "images", ignore = true)
    void toUpdateProduct(@MappingTarget Product product, ProductRequest productRequest);
}
