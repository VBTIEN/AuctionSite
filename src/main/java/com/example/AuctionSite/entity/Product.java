package com.example.AuctionSite.entity;

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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    
    @Lob
    String description;

    @ManyToOne
    Status status;

    @ManyToMany
    @ToString.Exclude
    Set<Category> categories;

    @OneToMany
    @ToString.Exclude
    Set<Image> images;
}
