package com.example.AuctionSite.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
public class Ranks {
    @Id
    String name;
    String description;
    LocalTime rankTime;
    Integer rankPurchases;
    Integer rankSales;
    
    @ManyToMany
    @ToString.Exclude
    Set<Benefit> benefits;
    
    @OneToMany
    @ToString.Exclude
    Set<User> users;
}
