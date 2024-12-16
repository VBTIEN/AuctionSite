package com.example.AuctionSite.entity;

import java.time.Duration;
import java.time.LocalDate;
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

    @Lob
    String description;

    LocalDateTime startTime;
    LocalDateTime endTime;
    Integer finalCost;
    Integer numberOfBids;
    Integer participantCount;
    Duration remainingTime;
    LocalDate dateCreated;

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

    @ManyToOne
    Image image;

    @ManyToOne
    User winningBidder;

    @ManyToMany
    @JoinTable(
            name = "auction_participants",
            joinColumns = @JoinColumn(name = "auction_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    Set<User> participants;
}
