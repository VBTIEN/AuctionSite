package com.example.AuctionSite.dto.response;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
