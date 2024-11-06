package com.example.AuctionSite.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuctionResponse {
    String name;
    String description;
    LocalDateTime startTime;
    Integer finalCost;
    Integer numberOfBids;

    StatusResponse status;

    Set<BidResponse> bids;

    TimeResponse time;

    Set<NotificationResponse> notifications;

    ProductResponse product;

    CostResponse cost;

    StepResponse step;

    Set<FollowResponse> follows;
}
