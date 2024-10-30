package com.example.AuctionSite.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BidResponse {
    Integer bidMount;
    
    UserResponse user;
    
    AuctionResponse auction;
    
    Set<NotificationResponse> notifications;
}
