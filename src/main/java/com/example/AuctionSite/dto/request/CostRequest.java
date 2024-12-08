package com.example.AuctionSite.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CostRequest {
    @NotBlank(message = "COSTNAME_BLANK")
    @Size(max = 50, message = "COSTNAME_INVALID_SIZE")
    String name;

    @NotNull(message = "STARTCOST_BLANK")
    BigDecimal startCost;
}
