package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegulationRequest {
    @NotBlank(message = "REGULATION_NAME_BLANK")
    String name;

    @NotBlank(message = "REGULATION_DESCRIPTION_BLANK")
    String description;
}
