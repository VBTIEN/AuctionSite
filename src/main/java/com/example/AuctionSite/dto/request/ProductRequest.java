package com.example.AuctionSite.dto.request;

import com.example.AuctionSite.dto.response.CategoryResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotBlank(message = "")
    String name;
    
    @NotBlank(message = "")
    String description;
    
    @NotBlank(message = "")
    String status;
    
    Set<String> categories;
    
    Set<String> images;
}
