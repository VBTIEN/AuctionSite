package com.example.AuctionSite.dto.response;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Integer id;
    String name;
    String description;

    StatusResponse status;

    Set<CategoryResponse> categories;

    Set<ImageResponse> images;
}
