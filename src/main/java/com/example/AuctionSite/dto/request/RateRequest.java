package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RateRequest {
    @NotBlank(message = "")
    Float numberOfStar;
    
    @NotBlank(message = "")
    String description;
}