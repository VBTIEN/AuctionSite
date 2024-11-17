package com.example.AuctionSite.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
public class AuctionResponse {
    Integer id;
    String name;
    String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy")
    LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy")
    LocalDateTime endTime;

    Integer finalCost;
    Integer numberOfBids;
    String remainingTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate dateCreated;

    StatusResponse status;

    Set<BidResponse> bids;

    TimeResponse time;

    Set<NotificationResponse> notifications;

    ProductResponse product;

    CostResponse cost;

    StepResponse step;

    Set<FollowResponse> follows;

    ImageResponse image;
}
