package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BenefitRequest {
    @NotBlank(message = "BENEFITNAME_BLANK")
    @Size(max = 50, message = "BENEFITNAME_INVALID_SIZE")
    String name;

    @NotBlank(message = "BENEFIT_DESCRIPTION_BLANK")
    String description;
}
