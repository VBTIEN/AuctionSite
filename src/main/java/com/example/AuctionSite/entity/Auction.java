package com.example.AuctionSite.entity;

import java.time.Duration;
import java.time.LocalDateTime;
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
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String description;
    LocalDateTime startTime;
    Integer finalCost;
    Integer numberOfBids;
    Duration remainingTime;

    @ManyToOne
    Status status;

    @OneToMany(mappedBy = "auction")
    @ToString.Exclude
    Set<Bid> bids;

    @ManyToOne
    Time time;

    @ManyToMany
    @ToString.Exclude
    Set<Notification> notifications;

    @OneToOne
    Product product;

    @ManyToOne
    Cost cost;

    @ManyToOne
    Step step;

    @OneToMany(mappedBy = "auction")
    @ToString.Exclude
    Set<Follow> follows;
}
