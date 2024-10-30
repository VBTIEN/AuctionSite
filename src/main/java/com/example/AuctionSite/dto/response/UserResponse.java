package com.example.AuctionSite.dto.response;

import com.example.AuctionSite.entity.*;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
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
