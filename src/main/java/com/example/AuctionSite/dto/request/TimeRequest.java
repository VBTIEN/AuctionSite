package com.example.AuctionSite.dto.request;

import java.time.Duration;

import jakarta.validation.constraints.NotBlank;
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
    Duration time;
}
