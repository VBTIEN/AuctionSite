package com.example.AuctionSite.dto.response;

import com.example.AuctionSite.entity.Product;
import com.example.AuctionSite.entity.Ranks;
import com.example.AuctionSite.entity.Rate;
import com.example.AuctionSite.entity.Role;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
    int purchases;
    int sales;
    
    Set<RoleResponse> roles;
    
    Ranks ranks;
    
    Rate rate;
    
}
