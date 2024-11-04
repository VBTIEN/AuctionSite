package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    @NotBlank(message = "")
    String name;
    
    @NotBlank(message = "")
    String description;
    
    @NotBlank(message = "")
    Set<String> permissions;
}
