package com.example.AuctionSite.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    String description;
    LocalDateTime timeStart;
    String status;
    int finalCost;
    int numberOfBids;
    
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
    
    @OneToMany(mappedBy = "auctions")
    @ToString.Exclude
    Set<Follow> follows;
}
