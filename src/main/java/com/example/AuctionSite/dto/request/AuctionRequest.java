package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuctionRequest {
    @NotBlank(message = "")
    String name;
    
    @NotBlank(message = "")
    String description;
    
    @FutureOrPresent(message = "")
    LocalDateTime startTime;
    
    @NotBlank(message = "")
    String time;
    
    @NotBlank(message = "")
    Integer product;
    
    @NotBlank(message = "")
    String cost;
    
    @NotBlank(message = "")
    String step;
}
