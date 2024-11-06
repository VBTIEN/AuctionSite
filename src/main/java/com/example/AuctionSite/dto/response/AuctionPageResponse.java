package com.example.AuctionSite.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuctionPageResponse {
    List<AuctionResponse> auctions;
    int totalPages;
    long totalElements;
}