package com.example.AuctionSite.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StepRequest {
    @NotBlank(message = "STEPNAME_BLANK")
    @Size(max = 50, message = "STEPNAME_INVALID_SIZE")
    String name;

    @NotBlank(message = "STEP_BLANK")
    BigDecimal step;
}
