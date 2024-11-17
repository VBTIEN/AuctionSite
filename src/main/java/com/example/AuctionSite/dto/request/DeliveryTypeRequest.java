package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryTypeRequest {
    @NotBlank(message = "DELIVERYTYPE_NAME_BLANK")
    String name;

    @NotBlank(message = "DELIVERYTYPE_DESCRIPTION_BLANK")
    String description;
}
