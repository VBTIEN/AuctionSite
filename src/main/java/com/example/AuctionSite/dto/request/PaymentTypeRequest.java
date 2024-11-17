package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentTypeRequest {
    @NotBlank(message = "PAYMENTTYPE_NAME_BLANK")
    String name;

    @NotBlank(message = "PAYMENTTYPE_DESCRIPTION_BLANK")
    String description;
}
