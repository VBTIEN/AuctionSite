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
    int purchases;
    int sales;
    
    @ManyToMany
    Set<Role> roles;
    
    @ManyToOne
    Ranks ranks;
    
    @ManyToOne
    Rate rate;
}
