package com.example.AuctionSite.dto.response;

import java.time.Duration;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeResponse {
    String name;
    Duration time;
}
