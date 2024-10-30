package com.example.AuctionSite.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuctionResponse {
    String name;
    String description;
    LocalDateTime timeStart;
    String status;
    Integer finalCost;
    Integer numberOfBids;
    
    Set<BidResponse> bids;
    
    TimeResponse time;
    
    Set<NotificationResponse> notifications;
    
    ProductResponse product;
    
    CostResponse cost;
    
    StepResponse step;
    
    Set<FollowResponse> follows;
}
