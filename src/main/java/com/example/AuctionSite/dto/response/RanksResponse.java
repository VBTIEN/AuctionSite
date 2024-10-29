package com.example.AuctionSite.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RanksResponse {
    String name;
    String description;
    LocalTime rankTime;
    int rankPurchases;
    int rankSales;
    
    Set<BenefitResponse> benefits;
}
