package com.example.AuctionSite.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BidRequest {
    @NotNull(message = "BIDMOUNT_BLANK")
    BigDecimal bidMount;
}
