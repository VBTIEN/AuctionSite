package com.example.AuctionSite.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import com.example.AuctionSite.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate dob;

    String fullName;
    String phoneNumber;
    String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
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
