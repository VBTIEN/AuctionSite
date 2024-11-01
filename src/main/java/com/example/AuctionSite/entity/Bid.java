package com.example.AuctionSite.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer bidMount;
    
    @ManyToOne
    User user;
    
    @ManyToOne
    Auction auction;
    
    @ManyToMany
    @ToString.Exclude
    Set<Notification> notifications;
}
