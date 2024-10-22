package com.example.AuctionSite.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    
    @OneToOne
    Time time;
    
    @OneToOne
    ToAuction toAuction;
    
    @ManyToMany
    @ToString.Exclude
    List<Notice> notices;
}
