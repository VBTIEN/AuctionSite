package com.example.AuctionSite.dto.response;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BidResponse {
    Integer id;
    BigDecimal bidMount;
    String result;
    String by_user;
    String bid_of;

    Set<NotificationResponse> notifications;
}
