package com.example.AuctionSite.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String phoneNumber;
    String address;
    LocalDate joiningDate;

    @Builder.Default
    Duration actionTime = Duration.ZERO;

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

    @OneToMany
    @ToString.Exclude
    Set<Bid> bids;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    Set<Follow> follows;

    @ManyToOne
    Status status;

    @ManyToMany(mappedBy = "participants")
    @ToString.Exclude
    Set<Auction> joinedAuctions;

    @OneToMany
    @ToString.Exclude
    Set<Receipt> salesReceipt;

    @OneToMany
    @ToString.Exclude
    Set<Receipt> purchaseReceipt;

    @OneToMany
    @ToString.Include
    Set<Product> productSuccessfullyAuctioned;
}
