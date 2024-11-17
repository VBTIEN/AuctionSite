package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeRequest {
    @NotBlank(message = "TIMENAME_BLANK")
    @Size(max = 50, message = "TIMENAME_INVALID_SIZE")
    String name;

    @NotBlank(message = "TIME_BLANK")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):([0-5]?[0-9])$", message = "TIME_INVALID_FORMAT")
    String time;
}
