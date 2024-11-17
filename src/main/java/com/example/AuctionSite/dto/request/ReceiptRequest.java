package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiptRequest {
    @NotBlank(message = "")
    String name;

    @NotBlank(message = "")
    String description;

    @NotBlank(message = "")
    String deliveryType;

    @NotBlank(message = "")
    String paymentType;
}
