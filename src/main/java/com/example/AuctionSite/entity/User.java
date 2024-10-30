package com.example.AuctionSite.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
    
    @ManyToMany
    @ToString.Exclude
    Set<Role> roles;
    
    @ManyToOne
    Ranks ranks;
    
    @ManyToOne
    Rate rate;
    
    @OneToMany
    @ToString.Exclude
    Set<Product> products;
    
    @ManyToMany
    @ToString.Exclude
    Set<Notification> notifications;
    
    @OneToMany
    @ToString.Exclude
    Set<Auction> auctions;
    
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    Set<Bid> bids;
    
    @OneToMany(mappedBy = "users")
    @ToString.Exclude
    Set<Follow> follows;
}
