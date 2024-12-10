package com.example.AuctionSite.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuctionRequest {
    @NotBlank(message = "AUCTIONNAME_BLANK")
    @Size(max = 50, message = "AUCTIONNAME_INVALID_SIZE")
    String name;

    @NotBlank(message = "AUCTION_DESCRIPTION_BLANK")
    String description;

    //@FutureOrPresent(message = "STARTTIME_NOT_IN_FUTURE_OR_PRESENT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy")
    LocalDateTime startTime;

    @NotBlank(message = "AUCTION_TIME_BLANK")
    String time;

    @NotNull(message = "AUCTION_PRODUCT_BLANK")
    Integer product;

    @NotBlank(message = "AUCTION_COST_BLANK")
    String cost;

    @NotBlank(message = "AUCTION_STEP_BLANK")
    String step;

    Integer imageId;
}
