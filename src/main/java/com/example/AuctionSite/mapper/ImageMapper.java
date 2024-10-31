package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.request.ImageRequest;
import com.example.AuctionSite.dto.response.ImageResponse;
import com.example.AuctionSite.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageURL", ignore = true)
    Image toImage(ImageRequest imageRequest);
    
    ImageResponse toImageResponse(Image image);
    
    @Mapping(target = "id", ignore = true)
    Image toImage(ImageResponse imageResponse);
}
