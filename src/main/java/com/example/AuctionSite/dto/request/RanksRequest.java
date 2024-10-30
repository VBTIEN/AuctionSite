package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RanksRequest {
    @NotBlank(message = "")
    String name;
    
    @NotBlank(message = "")
    String description;
    
    @NotBlank(message = "")
    LocalTime rankTime;
    
    @NotBlank(message = "")
    Integer rankPurchases;
    
    @NotBlank(message = "")
    Integer rankSales;
    
    Set<String> benefits;
}
