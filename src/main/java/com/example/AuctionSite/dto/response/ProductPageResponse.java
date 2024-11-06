package com.example.AuctionSite.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductPageResponse {
    List<ProductResponse> products;
    int totalPages;
    long totalElements;
}
