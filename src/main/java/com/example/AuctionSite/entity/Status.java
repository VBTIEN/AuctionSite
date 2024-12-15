package com.example.AuctionSite.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Lob;
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
public class Status {
    @Id
    String name;

    @Lob
    String description;
}
