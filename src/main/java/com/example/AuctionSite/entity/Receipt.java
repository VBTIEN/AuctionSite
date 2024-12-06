package com.example.AuctionSite.entity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

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
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String description;
    BigDecimal sellingPrice;
    LocalDateTime receiptTime;
    Duration remainingTimeToPayment;

    @OneToOne
    Product product;

    @ManyToOne
    User seller;

    @ManyToOne
    User buyer;

    @ManyToOne
    DeliveryType deliveryType;

    @ManyToOne
    PaymentType paymentType;

    @ManyToOne
    Status status;

    @ManyToOne
    Time timeToPayment;
}
