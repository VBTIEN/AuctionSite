package com.example.AuctionSite.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import com.example.AuctionSite.entity.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String password;
    String email;
    LocalDate dob;
    String fullName;
    LocalDate joiningDate;
    LocalTime actionTime;
    Integer purchases;
    Integer sales;
    Integer sumOfRate;
    Integer numberOfRate;

    Set<RoleResponse> roles;

    RanksResponse ranks;

    RateResponse rate;

    Set<ProductResponse> products;

    Set<NotificationResponse> notifications;

    Set<AuctionResponse> auctions;

    Set<BidResponse> bids;

    Set<FollowResponse> follows;
}
