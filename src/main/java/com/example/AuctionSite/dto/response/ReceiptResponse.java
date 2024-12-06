package com.example.AuctionSite.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceiptResponse {
    Integer id;
    String name;
    String description;
    BigDecimal sellingPrice;
    String remainingTimeToPayment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy")
    LocalDateTime receiptTime;

    ProductResponse product;

    UserResponse seller;

    UserResponse buyer;

    DeliveryTypeResponse deliveryType;

    PaymentTypeResponse paymentType;

    StatusResponse status;

    TimeResponse timeToPayment;
}
