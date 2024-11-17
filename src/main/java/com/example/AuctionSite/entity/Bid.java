package com.example.AuctionSite.entity;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    BigDecimal bidMount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    User user;

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    @JsonIgnore
    Auction auction;

    @ManyToMany
    @ToString.Exclude
    Set<Notification> notifications;
}
